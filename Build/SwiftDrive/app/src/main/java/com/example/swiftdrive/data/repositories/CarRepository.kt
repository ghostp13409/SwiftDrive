package com.example.swiftdrive.data.repositories

import android.content.Context
import com.example.swiftdrive.data.dbhelpers.CarDatabaseHelper
import com.example.swiftdrive.data.models.Car
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Repository for managing cars in the local database and syncing with Firestore
class CarRepository(context: Context) {
    private val db = FirebaseFirestore.getInstance()
    val carsRef = db.collection("cars")
    private val localDb = CarDatabaseHelper(context)

    // Fetch from Firestore and store locally
    suspend fun fetchAndStoreCars() {
        val remoteCars = carsRef.get().await().documents.mapNotNull { it.toObject(Car::class.java) }
        // Clear local and store remote
        localDb.writableDatabase.execSQL("DELETE FROM cars")
        remoteCars.forEach { car ->
            localDb.insertCar(
                    car.id,
                    car.year,
                    car.make,
                    car.model,
                    car.pricePerDay,
                    car.isAvailable,
                    car.engineType,
                    car.condition,
                    car.category,
                    car.tier,
                    car.imageRes
            )
        }
    }

    // Get cars from local DB
    fun getCars(): List<Car> {
        return localDb.getAllCars()
    }

    // Add car locally and mark for sync
    fun addCar(car: Car) {
        localDb.insertCar(
                car.id,
                car.year,
                car.make,
                car.model,
                car.pricePerDay,
                car.isAvailable,
                car.engineType,
                car.condition,
                car.category,
                car.tier,
                car.imageRes
        )
    }

    // Update car locally and mark for sync
    fun updateCar(car: Car) {
        localDb.updateCar(car)
    }

    // Delete car locally and mark for sync
    fun deleteCar(car: Car) {
        localDb.deleteCar(car.id)
    }

    suspend fun getCarById(carId: String): Car? {
        return localDb.getAllCars().find { it.id.toString() == carId }
    }

    // Sync local changes to Firestore
    suspend fun syncToFirestore() {
        val localCars = localDb.getAllCars()
        // For simplicity, replace all in Firestore with local data
        // In a real app, you'd track changes
        localCars.forEach { car -> carsRef.document(car.id.toString()).set(car).await() }
    }
}
