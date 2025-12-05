package com.example.swiftdrive.data.repositories

import android.content.Context
import com.example.swiftdrive.data.dbhelpers.CustomerDatabaseHelper
import com.example.swiftdrive.data.models.Customer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CustomerRepository(context: Context) {
    private val db = FirebaseFirestore.getInstance()
    val customersRef = db.collection("customers")
    private val localDb = CustomerDatabaseHelper(context)

    // Fetch from Firestore and store locally
    suspend fun fetchAndStoreCustomers() {
        val remoteCustomers = customersRef.get().await().documents.mapNotNull { it.toObject(Customer::class.java) }
        // Clear local and store remote
        localDb.writableDatabase.execSQL("DELETE FROM customer")
        remoteCustomers.forEach { customer ->
            localDb.insertCustomer(
                customer.roles,
                customer.firstName,
                customer.lastName,
                customer.age,
                customer.phoneNumber,
                customer.drivingLicence,
                customer.email,
                customer.password
            )
        }
    }

    // Get customers from local DB
    fun getCustomers(): List<Customer> {
        return localDb.getAllCustomers()
    }

    // Add customer locally and mark for sync
    fun addCustomer(customer: Customer) {
        localDb.insertCustomer(
            customer.roles,
            customer.firstName,
            customer.lastName,
            customer.age,
            customer.phoneNumber,
            customer.drivingLicence,
            customer.email,
            customer.password
        )
        // TODO: Mark for sync
    }

    // Update customer locally and mark for sync
    fun updateCustomer(customer: Customer) {
        localDb.updateCustomer(
            customer.id,
            customer.roles,
            customer.firstName,
            customer.lastName,
            customer.age,
            customer.phoneNumber,
            customer.drivingLicence,
            customer.email,
            customer.password
        )
        // TODO: Mark for sync
    }

    // Delete customer locally and mark for sync
    fun deleteCustomer(customer: Customer) {
        localDb.deleteCustomer(customer.id)
        // TODO: Mark for sync
    }

    suspend fun getCustomerById(customerId: Int): Customer? {
        return localDb.getAllCustomers().find { it.id == customerId }
    }

    // Sync local changes to Firestore
    suspend fun syncToFirestore() {
        val localCustomers = localDb.getAllCustomers()
        // For simplicity, replace all in Firestore with local data
        localCustomers.forEach { customer ->
            customersRef.document(customer.id.toString()).set(customer).await()
        }
    }
}