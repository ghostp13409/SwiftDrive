package com.example.swiftdrive.features.cars

sealed class CarsValidationResult {
    object Success : CarsValidationResult()
    data class Error(val message: String) : CarsValidationResult()

}