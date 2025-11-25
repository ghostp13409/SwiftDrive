package com.example.swiftdrive.data.models

data class Rentals (
    val id: String,
    val userId: String,
    val carId: String,
    val rentalStart: String,
    val rentalEnd: String,
    val totalCost: Double,
    val status: String
)