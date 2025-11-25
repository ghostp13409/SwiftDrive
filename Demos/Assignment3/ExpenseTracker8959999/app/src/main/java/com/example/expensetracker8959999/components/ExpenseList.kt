package com.example.expensetracker8959999.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.expensetracker8959999.data.Expense


// Expense List Composable to display a list of expenses
@Composable
fun ExpenseList(
    expenses: List<Expense>,
    onEdit: (Expense) -> Unit,
    onDelete: (Expense) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expenses, key = { it.id }) { expense ->
            ExpenseCard(
                expense = expense,
                onEditClick = { onEdit(expense) },      // Get from ExpenseCard
                onDeleteClick = { onDelete(expense) }   // Get from ExpenseCard
            )
        }
    }
}