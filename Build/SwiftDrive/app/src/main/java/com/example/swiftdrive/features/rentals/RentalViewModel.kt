package com.example.swiftdrive.features.rentals

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.swiftdrive.data.dbhelpers.CarDatabaseHelper
import com.example.swiftdrive.data.dbhelpers.CustomerDatabaseHelper
import com.example.swiftdrive.data.dbhelpers.RentalDatabaseHelper
import com.example.swiftdrive.data.models.Car
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.Rentals
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class RentalViewModel(application: Application) : AndroidViewModel(application) {

    private val rentalDbHelper = RentalDatabaseHelper(application)
    private val carDbHelper = CarDatabaseHelper(application)
    private val customerDbHelper = CustomerDatabaseHelper(application)

    var rentals = mutableStateOf<List<Rentals>>(emptyList())
        private set

    var customers = mutableStateOf<List<Customer>>(emptyList())
        private set

    var cars = mutableStateOf<List<Car>>(emptyList())
        private set

    var selectedRental by mutableStateOf<Rentals?>(null)
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
        seedRentals()
    }

    fun selectRental(rental: Rentals) {
        selectedRental = rental
    }

    fun loadRentals() {
        rentals.value = rentalDbHelper.getAllRentals()
    }

    fun loadCustomers() {
        customers.value = customerDbHelper.getAllCustomers()
    }

    fun loadCars() {
        cars.value = carDbHelper.getAllCars()
    }

    fun seedRentals() {
        if (rentalDbHelper.getAllRentals().isEmpty()) {
            rentalDbHelper.seedRentals()
            loadRentals()
        } else {
            loadRentals()
        }
    }

    fun updateSelectedCustomer(customer: Customer?) {
        selectedCustomer = customer
        calculateTotalCost()
    }

    fun updateSelectedCar(car: Car?) {
        selectedCar = car
        calculateTotalCost()
    }

    fun updateRentalStart(date: String) {
        rentalStart = date
        calculateTotalCost()
    }

    fun updateRentalEnd(date: String) {
        rentalEnd = date
        calculateTotalCost()
    }

    fun updateStatus(newStatus: String) {
        status = newStatus
    }

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
        val id = System.currentTimeMillis().toString()
        rentalDbHelper.insertRental(
            id,
            selectedCustomer!!.id.toString(),
            selectedCar!!.id.toString(),
            rentalStart,
            rentalEnd,
            totalCost.toDouble(),
            status
        )
        // Mark car as unavailable
        carDbHelper.updateCarAvailability(selectedCar!!.id, false)
        loadRentals()
        loadCars() 
        resetInputFields()
        return true
    }

    fun deleteRental(id: String) {
        rentalDbHelper.deleteRental(id)
        loadRentals()
    }

    fun returnCar(rentalId: String) {
        rentalDbHelper.updateRentalStatus(rentalId, "Completed")
        // Find the car for this rental and mark it as available
        val rental = rentals.value.find { it.id == rentalId }
        rental?.let {
            val carId = it.carId.toIntOrNull()
            carId?.let { id ->
                carDbHelper.updateCarAvailability(id, true)
            }
        }
        loadRentals()
        loadCars() 
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