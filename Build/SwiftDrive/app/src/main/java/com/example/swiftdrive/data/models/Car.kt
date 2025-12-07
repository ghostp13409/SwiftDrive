package com.example.swiftdrive.data.models

import com.google.firebase.firestore.IgnoreExtraProperties

// Enum classes for representing different car features
enum class EngineType {
    DIESEL,
    PETROL,
    HYBRID,
    ELECTRIC,
}

// Enum for representing car condition
enum class Condition {
    NEW,
    USED,
}

// Enum for representing car category
enum class Category {
    SEDAN,
    SUV,
    HATCHBACK,
    COUPE,
    MINIVAN
}

// Enum for representing car tier
enum class Tier {
    Economy,
    PREMIUM,
    LUXURY
}

// Data class for representing a car
@IgnoreExtraProperties
data class Car(
        val id: Int = 0,
        val year: Int = 0,
        val make: String = "",
        val model: String = "",
        val pricePerDay: Double = 0.0,
        val isAvailable: Boolean = true,
        val engineType: EngineType = EngineType.PETROL,
        val condition: Condition = Condition.NEW,
        val category: Category = Category.SEDAN,
        val tier: Tier = Tier.Economy,
        val imageRes: Int = 0,
) {
    constructor() :
            this(
                    0,
                    0,
                    "",
                    "",
                    0.0,
                    true,
                    EngineType.PETROL,
                    Condition.NEW,
                    Category.SEDAN,
                    Tier.Economy,
                    0
            )
}
