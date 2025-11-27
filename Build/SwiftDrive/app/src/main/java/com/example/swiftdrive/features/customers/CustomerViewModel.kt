package com.example.swiftdrive.features.customers

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.swiftdrive.data.dbhelpers.CustomerDatabaseHelper
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.UserRoles

class CustomerViewModel(application: Application): AndroidViewModel(application)
{

    private val dbHelper= CustomerDatabaseHelper(application)
    var customers by mutableStateOf<List<Customer>>(emptyList())
        private set

    var roles by mutableStateOf("")
        private set
    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var age  by mutableIntStateOf(0)
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var drivingLicence by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var editingId: Int? by mutableStateOf(null)
        private set


    init {
        seedCustomers()
    }

    fun loadCustomers() {
        customers = dbHelper.getAllCustomers()
        Log.d("CustomerViewModel", "Loaded ${customers.size} customers.")
    }

    fun seedCustomers() {
        if (dbHelper.getAllCustomers().isEmpty()) {
            dbHelper.insertCustomer(UserRoles.USER, "John", "Doe", 25, "123-456-7890", "DL123456", "john.doe@example.com", "password1")
            dbHelper.insertCustomer(UserRoles.USER, "Jane", "Smith", 30, "098-765-4321", "DL654321", "jane.smith@example.com", "password2")
            dbHelper.insertCustomer(UserRoles.ADMIN, "Admin", "User", 35, "111-222-3333", "DL999999", "admin@example.com", "adminpass")
            loadCustomers()
        } else {
            loadCustomers()
        }
    }

    fun editCustomer(customer: Customer){

        roles=customer.roles.toString()
        editingId=customer.id
        firstName=customer.firstName
        lastName=customer.lastName
        age=customer.age
        phoneNumber=customer.phoneNumber
        drivingLicence=customer.drivingLicence.toString()
        email=customer.email
        password=customer.password
    }
    fun deleteCustomer(id: Int){
        dbHelper.deleteCustomer(id)
        loadCustomers()
    }
}
