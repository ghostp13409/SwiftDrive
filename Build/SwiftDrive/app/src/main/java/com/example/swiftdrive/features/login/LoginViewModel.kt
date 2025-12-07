package com.example.swiftdrive.features.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.data.models.UserRoles
import com.example.swiftdrive.data.repositories.CustomerRepository
import com.example.swiftdrive.navigation.SessionManager
import kotlinx.coroutines.launch

// LOGIN VIEW MODEL
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val customerRepository = CustomerRepository(application)
    private val sessionManager = SessionManager(application)

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // functions for changing the mail and password
    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    // function for login
    fun onLoginClick(onLoginSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Email and password cannot be empty"
            return
        }
        isLoading = true
        viewModelScope.launch {
            val customers = customerRepository.getCustomers()
            val customer =
                    customers.find {
                        it.email == email && it.password == password && it.roles == UserRoles.ADMIN
                    }
            isLoading = false
            if (customer != null) {
                sessionManager.createLoginSession(customer.id, customer.email, customer.roles.name)
                onLoginSuccess()
            } else {
                errorMessage = "Invalid email or password, or not an admin"
            }
        }
    }

    fun fetchCustomers() {
        viewModelScope.launch { customerRepository.fetchAndStoreCustomers() }
    }
}
