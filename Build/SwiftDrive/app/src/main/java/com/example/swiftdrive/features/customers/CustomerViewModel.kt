package com.example.swiftdrive.features.customers

import android.app.Application
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
    var customers by mutableStateOf<List<Customer>>(dbHelper.getAllCustomers())
        private set

    var roles by mutableStateOf(UserRoles.USER)
        private set
    fun updateRoles(newRole: UserRoles) {
        roles = newRole
    }

    var firstName by mutableStateOf("")
        private set
    fun updateFirstName(newFirstName: String) {
        firstName = newFirstName
    }

    var lastName by mutableStateOf("")
        private set
    fun updateLastName(newLastName: String) {
        lastName = newLastName
    }

    var age  by mutableIntStateOf(0)
        private set
    fun updateAge(newAgeString: String) {

        age = newAgeString.toIntOrNull() ?: 0
    }
    var phoneNumber by mutableStateOf("")
        private set

    fun  updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }

    var drivingLicence by mutableStateOf("")
        private set

    fun  updateDrivingLicence(newDrivingLicence: String) {
        drivingLicence = newDrivingLicence
    }

    var email by mutableStateOf("")
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    var password by mutableStateOf("")
        private set

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    var editingId: Int? by mutableStateOf(null)
        private set

    var errorMessage by mutableStateOf("")
        private set


    var showError by mutableStateOf(false)
        private set

    fun loadCustomers() {
        customers = dbHelper.getAllCustomers()
    }


    fun editCustomer(customer: Customer){

        roles=customer.roles
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

    fun saveCustomer(){
        errorMessage = ""
        if (validateInput()) {

            // Edit existing expense
            if (editingId != null) {
                dbHelper.updateCustomer(
                    id = editingId!!,
                    roles = roles,
                    firstName=firstName,
                    lastName=lastName,
                    age=age,
                    phoneNumber=phoneNumber,
                    drivingLicence=drivingLicence,
                    email=email,
                    password=password
                )
                editingId = null
            } else {
                dbHelper.insertCustomer(

                    roles = roles,
                    firstName=firstName,
                    lastName=lastName,
                    age=age,
                    phoneNumber=phoneNumber,
                    drivingLicence=drivingLicence,
                    email=email,
                    password=password

                )
            }
            loadCustomers()
            clearInput()
            showError = false
        } else {
            showError = true
        }
    }

    fun validateInput(): Boolean {
        if (firstName.isEmpty()&& lastName.isEmpty()) {
            errorMessage = "First and Last Name Cannot be empty"
            return false
        }
        if (age==0||age<=16) {
            errorMessage = "Please enter a valid age "
            return false
        }
        if (phoneNumber.isEmpty()) {
            errorMessage = "Phone Number Cannot be empty"
            return false
        }
        if (email.isEmpty()) {
            errorMessage = "Email Cannot be empty"
            return false
        }
        if (password.isEmpty()) {
            errorMessage = "Password Cannot be empty"
            return false
        }
        if (errorMessage.isNotEmpty()) {
            return false
        }
        return true
    }

    fun clearInput() {
        roles = UserRoles.USER
        firstName=""
        lastName=""
        age=0
        phoneNumber=""
        drivingLicence=""
        email=""
        password=""
        errorMessage = ""
    }

}





