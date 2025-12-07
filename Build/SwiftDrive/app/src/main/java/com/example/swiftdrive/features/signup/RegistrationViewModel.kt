package com.example.swiftdrive.features.signup

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.data.dbhelpers.CustomerDatabaseHelper
import com.example.swiftdrive.data.models.UserRoles
import kotlinx.coroutines.launch

// Registration ViewModel
class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    // Customer DbHelper
    private val customerDbHelper = CustomerDatabaseHelper(application)

    // Registration form fields

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    var age by mutableStateOf("")
        private set

    var phoneNumber by mutableStateOf("")
        private set

    var drivingLicence by mutableStateOf("")
        private set

    var selectedRole by mutableStateOf(UserRoles.ADMIN)
        private set

    // Loading and Error Tracking Fields
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Setters
    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        errorMessage = null
    }

    fun onFirstNameChange(newFirstName: String) {
        firstName = newFirstName
        errorMessage = null
    }

    fun onLastNameChange(newLastName: String) {
        lastName = newLastName
        errorMessage = null
    }

    fun onAgeChange(newAge: String) {
        age = newAge
        errorMessage = null
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
        errorMessage = null
    }

    fun onDrivingLicenceChange(newDrivingLicence: String) {
        drivingLicence = newDrivingLicence
        errorMessage = null
    }

    fun onRoleChange(newRole: UserRoles) {
        selectedRole = newRole
        errorMessage = null
    }

    // Registration Click Handler
    fun onRegisterClick(onRegistrationSuccess: () -> Unit) {

        // Get Errors
        if (email.isBlank() ||
                        password.isBlank() ||
                        confirmPassword.isBlank() ||
                        firstName.isBlank() ||
                        lastName.isBlank() ||
                        age.isBlank() ||
                        phoneNumber.isBlank()
        ) {
            errorMessage = "All fields are required"
            return
        }
        if (password != confirmPassword) {
            errorMessage = "Passwords do not match"
            return
        }
        if (password.length < 6) {
            errorMessage = "Password must be at least 6 characters"
            return
        }
        val ageInt = age.toIntOrNull()
        if (ageInt == null || ageInt <= 0) {
            errorMessage = "Invalid age"
            return
        }
        // Basic phone number validation (allows digits, spaces, dashes, parentheses, and +)
        val phoneRegex = Regex("^[+]?[0-9\\s\\-\\(\\)]{7,15}$")
        if (!phoneRegex.matches(phoneNumber)) {
            errorMessage = "Invalid phone number format"
            return
        }

        isLoading = true
        viewModelScope.launch {
            // Check if email already exists
            val existingCustomers = customerDbHelper.getAllCustomers()
            if (existingCustomers.any { it.email == email }) {
                errorMessage = "Email already exists"
                isLoading = false
                return@launch
            }

            // Add customer to database
            customerDbHelper.insertCustomer(
                    roles = selectedRole,
                    firstName = firstName,
                    lastName = lastName,
                    age = ageInt,
                    phoneNumber = phoneNumber,
                    drivingLicence = drivingLicence.ifBlank { null },
                    email = email,
                    password = password
            )
            isLoading = false
            onRegistrationSuccess()
        }
    }
}
