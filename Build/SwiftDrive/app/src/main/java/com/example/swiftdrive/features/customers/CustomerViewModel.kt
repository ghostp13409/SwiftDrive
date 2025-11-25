package com.example.swiftdrive.features.customers

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.swiftdrive.data.dbhelpers.CustomerDatabaseHelper
import com.example.swiftdrive.data.models.Customer

class CustomerViewModel(application: Application): AndroidViewModel(application)
{

    private val dbHelper= CustomerDatabaseHelper(application)
    var customers by mutableStateOf<List<Customer>>(dbHelper.getAllCustomers())
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


    fun loadCustomers() {
        customers = dbHelper.getAllCustomers()
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



