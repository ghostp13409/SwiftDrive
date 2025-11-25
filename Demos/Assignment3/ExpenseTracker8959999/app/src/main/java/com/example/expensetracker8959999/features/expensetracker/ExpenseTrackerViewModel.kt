package com.example.expensetracker8959999.features.expensetracker

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.expensetracker8959999.data.Expense
import com.example.expensetracker8959999.data.ExpenseCategory
import com.example.expensetracker8959999.data.ExpenseDatabaseHelper


class ExpenseTrackerViewModel(application: Application): AndroidViewModel(application) {

    // Db Helper for expenses
    private val dbHelper = ExpenseDatabaseHelper(application)

    // List of expenses
    var expenses by mutableStateOf<List<Expense>>(dbHelper.getAllExpenses())
        private set

    // Expense fields for form
    var expenseTitle by mutableStateOf("")
        private set
    var expenseAmount by mutableStateOf("")
        private set

    var selectedExpenseCategory by mutableStateOf(ExpenseCategory.Food)
        private set

    // Fields for Error handling
    var showError by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set


    // Store currently editing expense
    var editingId: Int? by mutableStateOf(null)
        private set

    // Setters for expense fields
    fun updateExpenseTitle(newTitle: String) {
        expenseTitle = newTitle
    }

    fun updateExpenseAmount(newAmount: String) {
        expenseAmount = newAmount
    }

    fun updateSelectedCategory(newCategory: ExpenseCategory) {
        selectedExpenseCategory = newCategory
    }

    // Validate input and add errors
    fun validateInput(): Boolean {
        if (expenseTitle.isEmpty()) {
            errorMessage = "Title cannot be empty"
            return false
        }
        if (expenseAmount.isEmpty()) {
            errorMessage = "Amount cannot be empty"
            return false
        }
        if (expenseAmount.toDoubleOrNull() == null){
            errorMessage = "Amount must be a number"
            return false
        }
        if (errorMessage.isNotEmpty()) {
            return false
        }
        return true
    }

    // Load Expenses from db
    fun loadExpenses() {
        expenses = dbHelper.getAllExpenses()
    }

    // Save Expense to db
    fun saveExpense(){
        errorMessage = ""
        if (validateInput()) {

            // Edit existing expense
            if (editingId != null) {
                dbHelper.updateExpense(
                    id = editingId!!,
                    title = expenseTitle,
                    amount = expenseAmount.toDouble(),
                    category = selectedExpenseCategory
                )
                editingId = null
            } else {
                dbHelper.insertExpense(
                    title = expenseTitle,
                    amount = expenseAmount.toDouble(),
                    category = selectedExpenseCategory
                )
            }
            loadExpenses()
            clearExpense()
            showError = false
        } else {
            showError = true
        }
    }


    // Select Expense for Editing
    fun selectEditExpense(expense: Expense){
        editingId = expense.id
        expenseTitle = expense.title
        expenseAmount = expense.amount.toString()
        selectedExpenseCategory = expense.category
    }

    // Delete an Expense
    fun deleteExpense(expense: Expense) {
        dbHelper.deleteExpense(expense.id)
        loadExpenses()
    }

    // Clear Expense fields
    fun clearExpense() {
        expenseTitle = ""
        expenseAmount = ""
        selectedExpenseCategory = ExpenseCategory.Food
        errorMessage = ""
    }

    // Seed Data Note: not part of the assignment(only using for seeding the data for testing purposes)
    fun seedData() {
        dbHelper.seedData()
        loadExpenses()
    }
}