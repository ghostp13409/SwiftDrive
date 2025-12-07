package com.example.swiftdrive.features.customers

sealed class CustomersValidationResult {
    object Success : CustomersValidationResult()
    data class Error(val message: String) : CustomersValidationResult()
}