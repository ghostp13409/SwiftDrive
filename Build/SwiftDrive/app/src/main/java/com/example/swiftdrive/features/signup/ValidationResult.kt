package com.example.swiftdrive.features.signup

// for validtion result
sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
