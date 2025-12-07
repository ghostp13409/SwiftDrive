package com.example.swiftdrive.data.models

import com.google.firebase.firestore.IgnoreExtraProperties

// Data class for representing a rental
@IgnoreExtraProperties
data class Rental (
    val id: Int = 0,
    val userId: Int = 0,
    val carId: Int = 0,
    val rentalStart: String = "",
    val rentalEnd: String = "",
    val totalCost: Double = 0.0,
    val status: String = ""
) {
    constructor() : this(0, 0, 0, "", "", 0.0, "")
}