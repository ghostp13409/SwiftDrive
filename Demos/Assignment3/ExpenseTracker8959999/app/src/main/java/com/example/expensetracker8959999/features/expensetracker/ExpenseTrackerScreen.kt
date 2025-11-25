package com.example.expensetracker8959999.features.expensetracker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker8959999.R
import com.example.expensetracker8959999.components.ExpenseForm
import com.example.expensetracker8959999.components.ExpenseList
import com.example.expensetracker8959999.data.ExpenseCategory
import com.example.expensetracker8959999.ui.theme.ExpenseTracker8959999Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerScreen(modifier: Modifier = Modifier) {
    val viewModel: ExpenseTrackerViewModel = viewModel()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Expense Form
        ExpenseForm(viewModel = viewModel)

        // Display error message if any
        if (viewModel.showError) {
            Text(
                text = viewModel.errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Expense List
        Text(
            text = stringResource(R.string.expense_list_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExpenseList(
            expenses = viewModel.expenses,
            onEdit = { viewModel.selectEditExpense(expense = it) },
            onDelete = {  viewModel.deleteExpense(expense = it) }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun ExpenseTrackerScreenPreview() {
    ExpenseTracker8959999Theme {
        ExpenseTrackerScreen()
    }
}