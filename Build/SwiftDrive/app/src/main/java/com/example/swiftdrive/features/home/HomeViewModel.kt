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

class HomeViewModel(application: Application): AndroidViewModel(application) {

    // Repositories for Remote Db
    private val carRepository = CarRepository(application)
    private val customerRepository = CustomerRepository(application)
    private val rentalRepository = RentalRepository(application)


    // Local Dbs



    var isSyncing by mutableStateOf(false)
        private set

    var hasUnsyncedChanges by mutableStateOf(false)
        private set

    var showSyncSuccess by mutableStateOf(false)
        private set

    var isSeeding by mutableStateOf(false)
        private set

    var showSeedSuccess by mutableStateOf(false)
        private set

    fun seedData() {
        if (isSeeding) return
        isSeeding = true
        viewModelScope.launch {
            try {
                // Sample customers
                val customers = listOf(
                    Customer(1, UserRoles.ADMIN, "John", "Doe", 30, "123-456-7890", "DL123456", "admin@example.com", "password"),
                    Customer(2, UserRoles.USER, "Jane", "Smith", 25, "987-654-3210", "DL654321", "jane@example.com", "password"),
                    Customer(3, UserRoles.USER, "Bob", "Johnson", 35, "555-123-4567", "DL789012", "bob@example.com", "password")
                )

                // Sample cars (matching the seedInitialCars from CarDatabaseHelper)
                val cars = listOf(
                    Car(1, 2023, "BMW", "3 Series", 120.0, true, EngineType.PETROL, Condition.NEW, Category.SEDAN, Tier.PREMIUM, R.drawable.bmw3_1),
                    Car(2, 2023, "BMW", "3 Series", 125.0, true, EngineType.PETROL, Condition.NEW, Category.SEDAN, Tier.PREMIUM, R.drawable.bmw3_2),
                    Car(3, 2023, "Ford", "Mustang", 140.0, true, EngineType.PETROL, Condition.NEW, Category.COUPE, Tier.PREMIUM, R.drawable.mustang_1),
                    Car(4, 2023, "Ford", "Mustang", 145.0, false, EngineType.PETROL, Condition.USED, Category.COUPE, Tier.PREMIUM, R.drawable.mustang_2),
                    Car(5, 2023, "Honda", "Civic", 60.0, true, EngineType.PETROL, Condition.USED, Category.SEDAN, Tier.Economy, R.drawable.civic_1),
                    Car(6, 2023, "Honda", "Civic", 58.0, true, EngineType.PETROL, Condition.USED, Category.SEDAN, Tier.Economy, R.drawable.civic_2),
                    Car(7, 2023, "Toyota", "Camry", 65.0, true, EngineType.HYBRID, Condition.NEW, Category.SEDAN, Tier.Economy, R.drawable.camry_1),
                    Car(8, 2023, "Toyota", "Camry", 68.0, true, EngineType.HYBRID, Condition.NEW, Category.SEDAN, Tier.Economy, R.drawable.camry_2),
                    Car(9, 2023, "Tesla", "Model 3", 150.0, true, EngineType.ELECTRIC, Condition.NEW, Category.SEDAN, Tier.PREMIUM, R.drawable.model3_1),
                    Car(10, 2023, "Tesla", "Model 3", 155.0, false, EngineType.ELECTRIC, Condition.NEW, Category.SEDAN, Tier.PREMIUM, R.drawable.model3_2),
                    Car(11, 2024, "Mercedes-Benz", "C-Class", 160.0, true, EngineType.PETROL, Condition.NEW, Category.SEDAN, Tier.LUXURY, R.drawable.cclass_1),
                    Car(12, 2024, "Mercedes-Benz", "C-Class", 165.0, true, EngineType.PETROL, Condition.NEW, Category.SEDAN, Tier.LUXURY, R.drawable.cclass_2)
                )

                // Sample rentals
                val rentals = listOf(
                    Rental(1, 2, 1, "2024-01-01", "2024-01-05", 225.0, "Completed"),
                    Rental(2, 3, 3, "2024-01-10", "2024-01-12", 160.0, "Completed"),
                    Rental(3, 2, 4, "2024-01-15", "2024-01-20", 500.0, "Active")
                )

                // Add to Firestore
                customers.forEach { customer ->
                    customerRepository.customersRef.document(customer.id.toString()).set(customer).await()
                }
                cars.forEach { car ->
                    carRepository.carsRef.document(car.id.toString()).set(car).await()
                }
                rentals.forEach { rental ->
                    rentalRepository.rentalsRef.document(rental.id.toString()).set(rental).await()
                }

                showSeedSuccess = true
            } finally {
                isSeeding = false
            }
        }
    }

    fun dismissSeedSuccess() {
        showSeedSuccess = false
    }

    fun fetchAllFromFirestore() {
        viewModelScope.launch {
            carRepository.fetchAndStoreCars()
            customerRepository.fetchAndStoreCustomers()
            rentalRepository.fetchAndStoreRentals()
        }
    }

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

    fun markAsChanged() {
        hasUnsyncedChanges = true
    }

    fun dismissSyncSuccess() {
        showSyncSuccess = false
    }
}