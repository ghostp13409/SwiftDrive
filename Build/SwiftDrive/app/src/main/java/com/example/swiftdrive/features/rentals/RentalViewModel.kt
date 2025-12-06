package com.example.swiftdrive.features.rentals

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.data.models.Car
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.Rental
import com.example.swiftdrive.data.repositories.CarRepository
import com.example.swiftdrive.data.repositories.CustomerRepository
import com.example.swiftdrive.data.repositories.RentalRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class RentalViewModel(application: Application, val onChange: (() -> Unit)? = null) : AndroidViewModel(application) {

    private val rentalRepository = RentalRepository(application)
    private val carRepository = CarRepository(application)
    private val customerRepository = CustomerRepository(application)

    var rentals = mutableStateOf<List<Rental>>(emptyList())
        private set

    var customers = mutableStateOf<List<Customer>>(emptyList())
        private set

    var cars = mutableStateOf<List<Car>>(emptyList())
        private set

    var selectedRental by mutableStateOf<Rental?>(null)
        private set

    // Input fields for adding rental
    var selectedCustomer by mutableStateOf<Customer?>(null)
        private set

    var selectedCar by mutableStateOf<Car?>(null)
        private set

    var rentalStart by mutableStateOf("")
        private set

    var rentalEnd by mutableStateOf("")
        private set

    var totalCost by mutableStateOf("")
        private set

    var status by mutableStateOf("Active")
        private set

    var errorMessage by mutableStateOf("")
        private set

    init {
        loadCustomers()
        loadCars()
        loadRentals()
    }

    fun selectRental(rental: Rental) {
        selectedRental = rental
    }

    fun loadRentals() {
        rentals.value = rentalRepository.getRentals()
    }

    fun loadCustomers() {
        customers.value = customerRepository.getCustomers()
    }

    fun loadCars() {
        cars.value = carRepository.getCars()
    }

    fun fetchFromFirestore() {
        viewModelScope.launch {
            rentalRepository.fetchAndStoreRentals()
            carRepository.fetchAndStoreCars()
            customerRepository.fetchAndStoreCustomers()
            loadRentals()
            loadCars()
            loadCustomers()
        }
    }

    fun syncToFirestore() {
        viewModelScope.launch {
            rentalRepository.syncToFirestore()
            carRepository.syncToFirestore()
            customerRepository.syncToFirestore()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedCustomer(customer: Customer?) {
        selectedCustomer = customer
        calculateTotalCost()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedCar(car: Car?) {
        selectedCar = car
        calculateTotalCost()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateRentalStart(date: String) {
        rentalStart = date
        calculateTotalCost()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateRentalEnd(date: String) {
        rentalEnd = date
        calculateTotalCost()
    }

    fun updateStatus(newStatus: String) {
        status = newStatus
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateTotalCost() {
        if (selectedCar != null && rentalStart.isNotEmpty() && rentalEnd.isNotEmpty()) {
            try {
                val startDate = LocalDate.parse(rentalStart, DateTimeFormatter.ISO_LOCAL_DATE)
                val endDate = LocalDate.parse(rentalEnd, DateTimeFormatter.ISO_LOCAL_DATE)
                val days = ChronoUnit.DAYS.between(startDate, endDate).toInt()
                if (days > 0) {
                    totalCost = (selectedCar!!.pricePerDay * days).toString()
                } else {
                    totalCost = "0.0"
                }
            } catch (e: Exception) {
                totalCost = "0.0"
            }
        } else {
            totalCost = "0.0"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addRental(): Boolean {
        errorMessage = ""
        if (selectedCustomer == null) {
            errorMessage = "Please select a customer."
            return false
        }
        if (selectedCar == null) {
            errorMessage = "Please select a car."
            return false
        }
        if (rentalStart.isEmpty() || rentalEnd.isEmpty()) {
            errorMessage = "Please select rental start and end dates."
            return false
        }
        try {
            val startDate = LocalDate.parse(rentalStart, DateTimeFormatter.ISO_LOCAL_DATE)
            val endDate = LocalDate.parse(rentalEnd, DateTimeFormatter.ISO_LOCAL_DATE)
            if (endDate.isBefore(startDate)) {
                errorMessage = "End date must be after start date."
                return false
            }
        } catch (e: Exception) {
            errorMessage = "Invalid date format."
            return false
        }

        // Generate ID
        val id = System.currentTimeMillis().toInt()
        val newRental = Rental(
            id = id,
            userId = selectedCustomer!!.id,
            carId = selectedCar!!.id,
            rentalStart = rentalStart,
            rentalEnd = rentalEnd,
            totalCost = totalCost.toDouble(),
            status = status
        )
        rentalRepository.addRental(newRental)
        // Mark car as unavailable
        val updatedCar = selectedCar!!.copy(isAvailable = false)
        carRepository.updateCar(updatedCar)
        loadRentals()
        loadCars()
        resetInputFields()
        onChange?.invoke()
        return true
    }

    fun deleteRental(id: Int) {
        val rentalToDelete = rentals.value.find { it.id == id } ?: return
        rentalRepository.deleteRental(rentalToDelete)
        loadRentals()
        onChange?.invoke()
    }

    fun returnCar(rentalId: Int) {
        val rental = rentals.value.find { it.id == rentalId } ?: return
        val updatedRental = rental.copy(status = "Completed")
        rentalRepository.updateRental(updatedRental)
        // Find the car for this rental and mark it as available
        val carId = rental.carId
        carId?.let { id ->
            val car = cars.value.find { it.id == id }
                car?.let {
                    val updatedCar = it.copy(isAvailable = true)
                    carRepository.updateCar(updatedCar)
                }
            }
            loadRentals()
            loadCars()
            onChange?.invoke()
    }

    private fun resetInputFields() {
        selectedCustomer = null
        selectedCar = null
        rentalStart = ""
        rentalEnd = ""
        totalCost = ""
        status = "Active"
        errorMessage = ""
    }
}