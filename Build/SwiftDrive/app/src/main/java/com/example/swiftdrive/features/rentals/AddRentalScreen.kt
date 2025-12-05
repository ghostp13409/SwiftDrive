package com.example.swiftdrive.features.rentals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.Car
import com.example.swiftdrive.data.models.Customer
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRentalScreen(modifier: Modifier, viewModel: RentalViewModel, onBackClick: () -> Unit = {}) {
    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 6.dp,
                tonalElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Add Rental",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        // Customer Dropdown
        var customerExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = customerExpanded,
            onExpandedChange = { customerExpanded = it }
        ) {
            OutlinedTextField(
                value = viewModel.selectedCustomer?.let { "${it.firstName} ${it.lastName}" } ?: "",
                onValueChange = {},
                label = { Text("Select Customer") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = customerExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = customerExpanded,
                onDismissRequest = { customerExpanded = false }
            ) {
                viewModel.customers.value.forEach { customer ->
                    DropdownMenuItem(
                        text = { Text("${customer.firstName} ${customer.lastName}") },
                        onClick = {
                            viewModel.updateSelectedCustomer(customer)
                            customerExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Car Dropdown
        var carExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = carExpanded,
            onExpandedChange = { carExpanded = it }
        ) {
            OutlinedTextField(
                value = viewModel.selectedCar?.let { "${it.year} ${it.make} ${it.model}" } ?: "",
                onValueChange = {},
                label = { Text("Select Car") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = carExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = carExpanded,
                onDismissRequest = { carExpanded = false }
            ) {
                viewModel.cars.value.filter { it.isAvailable }.forEach { car ->
                    DropdownMenuItem(
                        text = { Text("${car.year} ${car.make} ${car.model}") },
                        onClick = {
                            viewModel.updateSelectedCar(car)
                            carExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Rental Start Date
        var showStartDatePicker by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = viewModel.rentalStart,
            onValueChange = {},
            label = { Text("Rental Start Date") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                TextButton(onClick = { showStartDatePicker = true }) {
                    Text("Select")
                }
            }
        )

        if (showStartDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                            viewModel.updateRentalStart(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                        }
                        showStartDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showStartDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Rental End Date
        var showEndDatePicker by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = viewModel.rentalEnd,
            onValueChange = {},
            label = { Text("Rental End Date") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                TextButton(onClick = { showEndDatePicker = true }) {
                    Text("Select")
                }
            }
        )

        if (showEndDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                            viewModel.updateRentalEnd(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                        }
                        showEndDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Status Dropdown
        var statusExpanded by remember { mutableStateOf(false) }
        val statuses = listOf("Active", "Completed", "Cancelled")
        ExposedDropdownMenuBox(
            expanded = statusExpanded,
            onExpandedChange = { statusExpanded = it }
        ) {
            OutlinedTextField(
                value = viewModel.status,
                onValueChange = {},
                label = { Text("Status") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = statusExpanded,
                onDismissRequest = { statusExpanded = false }
            ) {
                statuses.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status) },
                        onClick = {
                            viewModel.updateStatus(status)
                            statusExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Total Cost Display
        OutlinedTextField(
            value = viewModel.totalCost,
            onValueChange = {},
            label = { Text("Total Cost") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Error Message
        if (viewModel.errorMessage.isNotEmpty()) {
            Text(
                text = viewModel.errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Button
        Button(
            onClick = {
                if (viewModel.addRental()) {
                    // Success, maybe navigate back or show message
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Rental")
        }
        }
    }
}