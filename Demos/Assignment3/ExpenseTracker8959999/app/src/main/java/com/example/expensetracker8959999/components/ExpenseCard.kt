package com.example.expensetracker8959999.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker8959999.data.Expense
import com.example.expensetracker8959999.data.ExpenseCategory
import com.example.expensetracker8959999.ui.theme.ExpenseTracker8959999Theme


// Expense Card Composable for displaying expense details

@Composable
fun ExpenseCard(expense: Expense, onEditClick: (id: Int) -> Unit, onDeleteClick: (id: Int) -> Unit) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "$${expense.amount}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = expense.category.toString(),
                    fontWeight = FontWeight.Light
                )
            }
        }

        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Edit",
            modifier = Modifier
                .clickable(
                    onClick = { onEditClick(expense.id) }
                )
        )

        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete",
            modifier = Modifier
                .clickable(
                    onClick = { onDeleteClick(expense.id) }
                ),
            tint = MaterialTheme.colorScheme.error
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    val expense = Expense(
        id = 1,
        title = "Groceries",
        amount = 50.0,
        category = ExpenseCategory.Food
    )
    ExpenseTracker8959999Theme {
        ExpenseCard(expense = expense, onEditClick = {}, onDeleteClick = {})
    }
}