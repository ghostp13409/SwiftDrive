package com.example.swiftdrive.features.customers

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.UserRoles
import com.example.swiftdrive.data.repositories.CustomerRepository
import com.example.swiftdrive.features.cars.CarsValidationResult
import kotlinx.coroutines.launch

// Customer View Model for Customer Screen and Customer Detail Screen and Add Customer Screen
class CustomerViewModel(application: Application, val onChange: (() -> Unit)? = null) :
    AndroidViewModel(application) {

    // Customer Repository
    private val customerRepository = CustomerRepository(application)

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var customers by mutableStateOf<List<Customer>>(emptyList())
        private set

    var selectedCustomer by mutableStateOf<Customer?>(null)
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

    var age by mutableIntStateOf(0)
        private set

    fun updateAge(newAgeString: String) {

        age = newAgeString.toIntOrNull() ?: 0
    }

    var phoneNumber by mutableStateOf("")
        private set

    fun updatePhoneNumber(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }

    var drivingLicence by mutableStateOf("")
        private set

    fun updateDrivingLicence(newDrivingLicence: String) {
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

//    var errorMessage by mutableStateOf("")
//        private set

    var showError by mutableStateOf(false)
        private set

    init {
        loadCustomers()
    }

    // Fetch from Firestore and update local
    fun fetchFromFirestore() {
        viewModelScope.launch {
            customerRepository.fetchAndStoreCustomers()
            loadCustomers()
        }
    }
    // Load from local
    fun loadCustomers() {
        customers = customerRepository.getCustomers()
        Log.d("CustomerViewModel", "Loaded ${customers.size} customers.")
    }
    // Sync to Firestore

    fun syncToFirestore() {
        viewModelScope.launch { customerRepository.syncToFirestore() }
    }

    // Seed Customer function for testing

    fun seedCustomers() {
        if (customerRepository.getCustomers().isEmpty()) {
            val john =
                Customer(
                    1,
                    UserRoles.USER,
                    "John",
                    "Doe",
                    25,
                    "123-456-7890",
                    "DL123456",
                    "john.doe@example.com",
                    "password1"
                )
            val jane =
                Customer(
                    2,
                    UserRoles.USER,
                    "Jane",
                    "Smith",
                    30,
                    "098-765-4321",
                    "DL654321",
                    "jane.smith@example.com",
                    "password2"
                )
            val admin =
                Customer(
                    3,
                    UserRoles.ADMIN,
                    "Admin",
                    "User",
                    35,
                    "111-222-3333",
                    "DL999999",
                    "admin@example.com",
                    "adminpass"
                )
            customerRepository.addCustomer(john)
            customerRepository.addCustomer(jane)
            customerRepository.addCustomer(admin)
            loadCustomers()
        } else {
            loadCustomers()
        }
    }

    // Select Customer for editing
    fun selectCustomer(customer: Customer) {
        selectedCustomer = customer

        roles = customer.roles
        editingId = customer.id
        firstName = customer.firstName
        lastName = customer.lastName
        age = customer.age
        phoneNumber = customer.phoneNumber
        drivingLicence = customer.drivingLicence.toString()
        email = customer.email
        password = customer.password
    }

    // Delete Customer
    fun deleteCustomer(id: Int) {
        val customerToDelete = customers.find { it.id == id } ?: return
        customerRepository.deleteCustomer(customerToDelete)
        loadCustomers()
        onChange?.invoke()
    }

    // delete Customer
    fun saveCustomer() {
        errorMessage = ""
//        if (validateInput()) {

        // Edit existing customer
        if (editingId != null) {
            val updatedCustomer =
                Customer(
                    id = editingId!!,
                    roles = roles,
                    firstName = firstName,
                    lastName = lastName,
                    age = age,
                    phoneNumber = phoneNumber,
                    drivingLicence = drivingLicence,
                    email = email,
                    password = password
                )
            customerRepository.updateCustomer(updatedCustomer)
            editingId = null
        } else {
            // New Random ID
            val id = System.currentTimeMillis().toInt()

            val newCustomer =
                Customer(
                    id = id,
                    roles = roles,
                    firstName = firstName,
                    lastName = lastName,
                    age = age,
                    phoneNumber = phoneNumber,
                    drivingLicence = drivingLicence,
                    email = email,
                    password = password
                )
            customerRepository.addCustomer(newCustomer)
        }
        loadCustomers()
        clearInput()
        showError = false
        onChange?.invoke()
//        } else {
//            showError = true
//        }
    }
    //    fun validateInput(): Boolean {
//        if (firstName.isEmpty() && lastName.isEmpty()) {
//            errorMessage = "First and Last Name Cannot be empty"
//            return false
//        }
//        if (age == 0 || age <= 16) {
//            errorMessage = "Please enter a valid age "
//            return false
//        }
//        if (phoneNumber.isEmpty()) {
//            errorMessage = "Phone Number Cannot be empty"
//            return false
//        }
//        if (email.isEmpty()) {
//            errorMessage = "Email Cannot be empty"
//            return false
//        }
//        if (password.isEmpty()) {
//            errorMessage = "Password Cannot be empty"
//            return false
//        }
//        if (errorMessage.isNotEmpty()) {
//            return false
//        }
//        return true
//    }
    // Celaring Input
    fun clearInput() {
        selectedCustomer = null
        editingId = null
        roles = UserRoles.USER
        firstName = ""
        lastName = ""
        age = 0
        phoneNumber = ""
        drivingLicence = ""
        email = ""
        password = ""
        errorMessage = ""
    }

    // For input validation
    fun onSaveCustomerClick(): Boolean {
        val validations = listOf(
            validateFirstName(firstName),
            validateLastName(lastName),
            validateEmail(email),
            validatePhoneNumber(phoneNumber),
            validateAge(age.toString()),
            validatePassword(password)
        )
        val firstError = validations.firstOrNull { it is CustomersValidationResult.Error }

        return if(firstError is CustomersValidationResult.Error){
            errorMessage = firstError.message
            false
        }
        else{
            errorMessage = null
            true
        }

    }

    // All validations
    private fun validateFirstName(firstName: String) =
        if (firstName.isBlank()) CustomersValidationResult.Error("First Name cannot be empty")
        else CustomersValidationResult.Success

    private fun validateLastName(lastName: String) =
        if (lastName.isBlank()) CustomersValidationResult.Error("Last Name cannot be empty")
        else CustomersValidationResult.Success

    private fun validateEmail(email: String) =
        if (email.isBlank()) CustomersValidationResult.Error("Email cannot be empty")
        else if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()))
            CustomersValidationResult.Error("Email must be in a valid format (example: example@example.com)")
        else CustomersValidationResult.Success

    private fun validatePhoneNumber(phoneNumber: String) =
        if (phoneNumber.isBlank()) CustomersValidationResult.Error("Phone Number cannot be empty")
        else if (!phoneNumber.matches("^(\\+1\\s?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$".toRegex()))
            CustomersValidationResult.Error("Phone Number must be in a valid format (example: 123-456-7890)")
        else CustomersValidationResult.Success

    private fun validateAge(age: String): CustomersValidationResult {
        if (age.isBlank()) {
            return CustomersValidationResult.Error("Age cannot be empty")
        }
        val ageValue = age.toIntOrNull()
        if (ageValue == null) {
            return CustomersValidationResult.Error("Age must be a number")
        }
        if (ageValue < 14 || ageValue > 100) {
            return CustomersValidationResult.Error("Age must be between 14 and 100")
        }
        return CustomersValidationResult.Success
    }

    private fun validatePassword(password: String): CustomersValidationResult {
        if (password.isBlank()) {
            return CustomersValidationResult.Error("Password cannot be empty")
        }
        if (password.length < 6) {
            return CustomersValidationResult.Error("Password must be at least 6 characters")
        }
        return CustomersValidationResult.Success
    }



}
