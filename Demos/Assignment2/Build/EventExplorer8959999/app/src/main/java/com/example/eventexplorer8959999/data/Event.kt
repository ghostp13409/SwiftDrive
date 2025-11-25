package com.example.eventexplorer8959999.data

// Event Model
data class Event (
    val name: String,
    val imageRes: Int,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val ticketPrice: Float,
    val category: EventCategories
)