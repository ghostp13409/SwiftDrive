package com.example.swiftdrive.features.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.swiftdrive.R
import com.example.swiftdrive.data.models.Category
import com.example.swiftdrive.data.models.Condition
import com.example.swiftdrive.data.models.EngineType
import com.example.swiftdrive.data.models.Tier

// Add Car Screen for adding or editing a car
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Add car screen function for adding or editing a car
fun AddCarScreen(
        onEventClick: () -> Unit,
        onBackClick: () -> Unit,
        modifier: Modifier = Modifier,
        viewModel: CarsViewModel
) {
    val isEditing = viewModel.selectedCar != null

    // Scaffold for the screen
    Scaffold(
            topBar = {
                Surface(
                        shadowElevation = 6.dp,
                        tonalElevation = 2.dp,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                ) {
                    TopAppBar(
                            title = {
                                Text(
                                        text = if (isEditing) "Edit Car" else "Add Car",
                                        style =
                                                androidx.compose.material3.MaterialTheme.typography
                                                        .titleLarge.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color =
                                                                androidx.compose.material3
                                                                        .MaterialTheme.colorScheme
                                                                        .onSurface
                                                )
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = onBackClick) {
                                    Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back",
                                            tint =
                                                    androidx.compose.material3.MaterialTheme
                                                            .colorScheme
                                                            .primary
                                    )
                                }
                            },
                            colors =
                                    TopAppBarDefaults.topAppBarColors(
                                            containerColor = Color.Transparent
                                    )
                    )
                }
            }
    ) { padding ->
        Column(
                modifier =
                        modifier.fillMaxSize()
                                .padding(padding)
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                    value = viewModel.year,
                    onValueChange = { viewModel.year = it },
                    label = { Text(stringResource(R.string.year)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                    value = viewModel.make,
                    onValueChange = { viewModel.make = it },
                    label = { Text(stringResource(R.string.Car_make)) },
                    modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                    value = viewModel.model,
                    onValueChange = { viewModel.model = it },
                    label = { Text(stringResource(R.string.car_model)) },
                    modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                    value = viewModel.pricePerDay,
                    onValueChange = { viewModel.pricePerDay = it },
                    label = { Text(stringResource(R.string.car_price_per_day)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            @OptIn(ExperimentalMaterial3Api::class)
            var engineExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                    expanded = engineExpanded,
                    onExpandedChange = { engineExpanded = it },
                    modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                        value = viewModel.engineType.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.engine_type)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = engineExpanded)
                        },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                        expanded = engineExpanded,
                        onDismissRequest = { engineExpanded = false }
                ) {
                    EngineType.values().forEach { engine ->
                        DropdownMenuItem(
                                text = { Text(engine.toString()) },
                                onClick = {
                                    viewModel.engineType = engine
                                    engineExpanded = false
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            @OptIn(ExperimentalMaterial3Api::class)
            var conditionExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                    expanded = conditionExpanded,
                    onExpandedChange = { conditionExpanded = it },
                    modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                        value = viewModel.condition.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.condition)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = conditionExpanded)
                        },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                        expanded = conditionExpanded,
                        onDismissRequest = { conditionExpanded = false }
                ) {
                    Condition.values().forEach { condition ->
                        DropdownMenuItem(
                                text = { Text(condition.toString()) },
                                onClick = {
                                    viewModel.condition = condition
                                    conditionExpanded = false
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            @OptIn(ExperimentalMaterial3Api::class)
            var categoryExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it },
                    modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                        value = viewModel.category.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.category)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                        },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                ) {
                    Category.values().forEach { category ->
                        DropdownMenuItem(
                                text = { Text(category.toString()) },
                                onClick = {
                                    viewModel.category = category
                                    categoryExpanded = false
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            @OptIn(ExperimentalMaterial3Api::class)
            var tierExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                    expanded = tierExpanded,
                    onExpandedChange = { tierExpanded = it },
                    modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                        value = viewModel.tier.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.tier)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = tierExpanded)
                        },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                        expanded = tierExpanded,
                        onDismissRequest = { tierExpanded = false }
                ) {
                    Tier.values().forEach { tier ->
                        DropdownMenuItem(
                                text = { Text(tier.toString()) },
                                onClick = {
                                    viewModel.tier = tier
                                    tierExpanded = false
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                        checked = viewModel.isAvailable,
                        onCheckedChange = { viewModel.isAvailable = it }
                )
                Text("Available")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                    onClick = {
                        if (isEditing) viewModel.updateCar() else viewModel.addCar()

                        onEventClick()
                    },
                    modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                        if (isEditing) stringResource(R.string.save_changes)
                        else stringResource(R.string.add_car)
                )
            }
        }
    }
}
