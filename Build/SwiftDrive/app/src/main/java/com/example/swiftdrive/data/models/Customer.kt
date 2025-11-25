package com.example.swiftdrive.data.models

enum class UserRoles{
    ADMIN,
    USER,
}



data class Customer (

    val id: Int,
    val roles: UserRoles,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val phoneNumber: String,

    val drivingLicence: String?,

    val email: String,
    val password: String,

)