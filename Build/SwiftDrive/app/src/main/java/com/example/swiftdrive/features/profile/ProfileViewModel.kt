package com.example.swiftdrive.features.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.repositories.CustomerRepository
import com.example.swiftdrive.data.repositories.RentalRepository
import com.example.swiftdrive.navigation.SessionManager
import kotlinx.coroutines.launch

// Profile View Model for Profile Page
class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val customerRepository = CustomerRepository(application)
    private val rentalRepository = RentalRepository(application)
    private val sessionManager = SessionManager(application)

    var currentUser by mutableStateOf<Customer?>(null)
        private set

    var totalRentals by mutableStateOf(0)
        private set

    var totalRevenue by mutableStateOf(0.0)
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadProfileData()
    }

    // function for Loading Profile Data
    private fun loadProfileData() {
        viewModelScope.launch {
            val userId = sessionManager.getUserId()
            if (userId != null) {
                // Fetch customers to get current user
                customerRepository.fetchAndStoreCustomers()
                currentUser = customerRepository.getCustomerById(userId)

                // Fetch rentals to calculate stats
                rentalRepository.fetchAndStoreRentals()
                val allRentals = rentalRepository.getRentals()
                val userRentals = allRentals.filter { it.userId == userId }
                totalRentals = userRentals.size
                totalRevenue = userRentals.sumOf { it.totalCost }
            }
            isLoading = false
        }
    }

    // function for refreshing data
    fun refreshData() {
        isLoading = true
        loadProfileData()
    }
}
