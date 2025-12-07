package com.example.swiftdrive.features.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.R
import com.example.swiftdrive.data.models.*
import com.example.swiftdrive.data.repositories.CarRepository
import com.example.swiftdrive.data.repositories.CustomerRepository
import com.example.swiftdrive.data.repositories.RentalRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.DirectionsCar
import com.example.swiftdrive.navigation.SessionManager

// View Model for Home Page
class HomeViewModel(application: Application): AndroidViewModel(application) {

    // Repositories for Remote Db
    private val carRepository = CarRepository(application)
    private val customerRepository = CustomerRepository(application)
    private val rentalRepository = RentalRepository(application)

    // Session Manager for tracking Current User
    private val sessionManager = SessionManager(application)



    // Sync Tracking Fields
    var isSyncing by mutableStateOf(false)
        private set

    var hasUnsyncedChanges by mutableStateOf(false)
        private set

    var showSyncSuccess by mutableStateOf(false)
        private set


    // Metrics Tracking Fields
    var totalCars by mutableStateOf(0)
        private set

    var activeRentals by mutableStateOf(0)
        private set

    var availableCars by mutableStateOf(0)
        private set

    var activeRentalsList by mutableStateOf<List<Rental>>(emptyList())
        private set

    var currentUserActiveRental by mutableStateOf<Rental?>(null)
        private set

    var isFetched by mutableStateOf(false)
        private set



    // Fetch Data from Firestore
    fun fetchAllFromFirestore() {
        viewModelScope.launch {
            carRepository.fetchAndStoreCars()
            customerRepository.fetchAndStoreCustomers()
            rentalRepository.fetchAndStoreRentals()
            updateCounts()
        }
    }

    // Setter
    fun updateIsFetched(fetchStatus: Boolean){
        isFetched = fetchStatus
    }

    // Update all metrics

    fun updateCounts() {
        val cars = carRepository.getCars()
        val rentals = rentalRepository.getRentals()
        val customers = customerRepository.getCustomers()
        val userId = sessionManager.getUserId()

        totalCars = cars.size
        activeRentals = rentals.count { it.status == "Active" }
        availableCars = cars.count { it.isAvailable }
        activeRentalsList = rentals.filter { it.status == "Active" }
        currentUserActiveRental = userId?.let { rentals.find { rental -> rental.userId == it && rental.status == "Active" } }

        hasUnsyncedChanges = false

    }

    // Sync Data to Firestore
    fun syncAllToFirestore() {
        if (isSyncing) return
        isSyncing = true
        viewModelScope.launch {
            try {
                carRepository.syncToFirestore()
                customerRepository.syncToFirestore()
                rentalRepository.syncToFirestore()
                hasUnsyncedChanges = false
                showSyncSuccess = true
            } finally {
                isSyncing = false
            }
        }
    }

    // Getters to get Cars and Customer from Rental
    fun getCarbyId(carId: Int): Car? {
        return carRepository.getCars().find { it.id == carId }
    }

    fun getCustomerById(customerId: Int): Customer? {
        return customerRepository.getCustomers().find { it.id == customerId }
    }



    fun markAsChanged() {
        hasUnsyncedChanges = true
    }

    fun dismissSyncSuccess() {
        showSyncSuccess = false
    }
}