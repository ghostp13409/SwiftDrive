package com.example.swiftdrive.data.repositories

import android.content.Context
import com.example.swiftdrive.data.dbhelpers.RentalDatabaseHelper
import com.example.swiftdrive.data.models.Rental
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Rental Repository for managing rentals in the local database and syncing with Firestore
class RentalRepository(context: Context) {
    private val db = FirebaseFirestore.getInstance()
    val rentalsRef = db.collection("rentals")
    private val localDb = RentalDatabaseHelper(context)

    // Fetch from Firestore and store locally
    suspend fun fetchAndStoreRentals() {
        val remoteRentals = rentalsRef.get().await().documents.mapNotNull { it.toObject(Rental::class.java) }
        // Clear local and store remote
        localDb.writableDatabase.execSQL("DELETE FROM rentals")
        remoteRentals.forEach { rental ->
            localDb.insertRental(
                rental.id,
                rental.userId,
                rental.carId,
                rental.rentalStart,
                rental.rentalEnd,
                rental.totalCost,
                rental.status
            )
        }
    }

    // Get rentals from local DB
    fun getRentals(): List<Rental> {
        return localDb.getAllRentals()
    }

    // Add rental locally and mark for sync
    fun addRental(rental: Rental) {
        localDb.insertRental(
            rental.id,
            rental.userId,
            rental.carId,
            rental.rentalStart,
            rental.rentalEnd,
            rental.totalCost,
            rental.status
        )
        // TODO: Mark for sync
    }

    // Update rental locally and mark for sync
    fun updateRental(rental: Rental) {
        localDb.updateRentalStatus(rental.id.toString(), rental.status)
        // TODO: Mark for sync
    }

    // Delete rental locally and mark for sync
    fun deleteRental(rental: Rental) {
        localDb.deleteRental(rental.id.toString())
        // TODO: Mark for sync
    }

    suspend fun getRentalById(rentalId: Int): Rental? {
        return localDb.getAllRentals().find { it.id == rentalId }
    }

    // Sync local changes to Firestore
    suspend fun syncToFirestore() {
        val localRentals = localDb.getAllRentals()
        // For simplicity, replace all in Firestore with local data
        localRentals.forEach { rental ->
            rentalsRef.document(rental.id.toString()).set(rental).await()
        }
    }
}