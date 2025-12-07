package com.example.swiftdrive.data.models

import com.google.firebase.firestore.IgnoreExtraProperties

// Enum classes for representing different user roles
enum class UserRoles {
    ADMIN,
    USER,
}

// Data class for representing a customer
@IgnoreExtraProperties
data class Customer(
        val id: Int = 0,
        val roles: UserRoles = UserRoles.USER,
        val firstName: String = "",
        val lastName: String = "",
        val age: Int = 0,
        val phoneNumber: String = "",
        val drivingLicence: String? = "",
        val email: String = "",
        val password: String = "",
) {
    constructor() : this(0, UserRoles.USER, "", "", 0, "", "", "", "")
}
