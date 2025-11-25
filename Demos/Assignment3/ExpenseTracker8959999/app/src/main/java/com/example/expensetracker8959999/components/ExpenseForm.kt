package com.example.expensetracker8959999.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker8959999.R
import com.example.expensetracker8959999.data.ExpenseCategory
import com.example.expensetracker8959999.features.expensetracker.ExpenseTrackerViewModel

// Expense Form Composable for Adding/Editing Expense
@Composable
fun ExpenseForm(viewModel: ExpenseTrackerViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Title Input
        Text(stringResource(R.string.txtField_title), fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = viewModel.expenseTitle,
            onValueChange = { viewModel.updateExpenseTitle(it) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("e.g., Grocery Shopping") }
        )

        // Amount Input
        Text(stringResource(R.string.txtField_amount), fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = viewModel.expenseAmount,
            onValueChange = { viewModel.updateExpenseAmount(it) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            placeholder = { Text("e.g., 50.00") }
        )

        // Category Selection
        Text(stringResource(R.string.txtField_category), fontWeight = FontWeight.Bold)
        val categories = ExpenseCategory.entries
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            categories.forEach { category ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { viewModel.updateSelectedCategory(category) }
                        .padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = (category == viewModel.selectedExpenseCategory),
                        onClick = { viewModel.updateSelectedCategory(category) }
                    )
                    Text(
                        text = category.name, // Using enum name (e.g., Food, Travel)
                        modifier = Modifier.padding(start = 4.dp), fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Save Button
        Button(
            onClick = { viewModel.saveExpense() },      // Get from viewModel
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007BFF) // Blue
            )
        ) {
            Text(stringResource(R.string.save_button), fontSize = 16.sp)
        }
    }
}