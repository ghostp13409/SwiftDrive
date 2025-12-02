package com.example.swiftdrive.data.models

data class Car (
    val id: Int = 0,
    val year: Int = 0,
    val make: String = "",
    val model: String = "",
    val pricePerDay: Double,
    val isAvailable: Boolean,
    val engineType: String,
    val condition: String,
    val category: String,
    val imageRes: Int,
)