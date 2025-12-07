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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.launch

class RentalViewModel(application: Application, val onChange: (() -> Unit)? = null) :
        AndroidViewModel(application) {

    //App Repositorys
    private val rentalRepository = RentalRepository(application)
    private val carRepository = CarRepository(application)
    private val customerRepository = CustomerRepository(application)

    // Initilize A list of different Variables in the application
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

    // Initiation function loads All customers, cars and rentals
    init {
        loadCustomers()
        loadCars()
        loadRentals()
    }
    // Selecting Rental
    fun selectRental(rental: Rental) {
        selectedRental = rental
    }

    // loading rentals
    fun loadRentals() {
        rentals.value = rentalRepository.getRentals()
    }

    // loading customer
    fun loadCustomers() {
        customers.value = customerRepository.getCustomers()
    }

    // loading cars
    fun loadCars() {
        cars.value = carRepository.getCars()
    }

    // fetching from firestore
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
    // syncing to firestore
    fun syncToFirestore() {
        viewModelScope.launch {
            rentalRepository.syncToFirestore()
            carRepository.syncToFirestore()
            customerRepository.syncToFirestore()
        }
    }

    // updating selected customer

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedCustomer(customer: Customer?) {
        selectedCustomer = customer
        calculateTotalCost()
    }
    // Basic setters and calculations

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedCar(car: Car?) {
        selectedCar = car
        calculateTotalCost()
    }
    // updating rental start and end

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateRentalStart(date: String) {
        rentalStart = date
        calculateTotalCost()
    }
    // updating rental end

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateRentalEnd(date: String) {
        rentalEnd = date
        calculateTotalCost()
    }
    // updates
    fun updateStatus(newStatus: String) {
        status = newStatus
    }
    // calculating total cost
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
    // this is where the rental is added
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
        val newRental =
                Rental(
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
    // deleting rental

    fun deleteRental(id: Int) {
        val rentalToDelete = rentals.value.find { it.id == id } ?: return
        rentalRepository.deleteRental(rentalToDelete)
        loadRentals()
        onChange?.invoke()
    }
    // returning car
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
    // resetting input fields
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
