package com.example.expensetracker8959999.data

// Enum representing expense categories
enum class ExpenseCategory {
    Food,
    Travel,
    Bills
}

// Class representing an expense
data class Expense(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory
)