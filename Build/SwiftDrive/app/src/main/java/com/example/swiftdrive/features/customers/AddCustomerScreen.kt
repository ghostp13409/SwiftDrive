package com.example.swiftdrive.features.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.swiftdrive.data.models.UserRoles


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(modifier: Modifier, viewModel: CustomerViewModel, onSaveClick: () -> Unit = {}, onBackClick: () -> Unit = {}) {

    val isEditing = viewModel.editingId != null
    val scrollState = rememberScrollState()

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
                            text = if (isEditing) "Edit Customer" else "Add Customer",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
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
                        containerColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        // This inner Column holds the form fields and takes up available vertical space.
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Adds space between each child composable.
        ) {
            // --- Form Input Fields ---
            OutlinedTextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.updateFirstName(it) },
                label = { Text("First Name") },
                placeholder = { Text("e.g., John") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.updateLastName(it) },
                label = { Text("Last Name") },
                placeholder = { Text("e.g., Doe") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email Address") },
                placeholder = { Text("e.g., john.doe@email.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = { Text("Phone Number") },
                placeholder = { Text("e.g., (555) 123-4567") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.age.toString(),
                onValueChange = { viewModel.updateAge(it) },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.drivingLicence,
                onValueChange = { viewModel.updateDrivingLicence(it) },
                label = { Text("Driving Licence (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Password") },
                // Hides the entered text for security.
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // --- Role Selection Dropdown ---
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = viewModel.roles.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Role") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    UserRoles.values().forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role.name) },
                            onClick = {
                                viewModel.updateRoles(role)
                                expanded = false
                            }
                        )
                    }
                }
            }

            // --- Informational Box ---
            InfoBox()

            // --- Action Buttons at the bottom ---
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Primary action button for saving the customer.
                Button(
                    onClick = {
                        viewModel.saveCustomer()
                        onSaveClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text(
                        if (isEditing) "Save Changes" else "Save Customer",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                // Secondary action button for cancelling the operation.
                OutlinedButton(
                    onClick = { viewModel.clearInput() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text("Cancel", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

/**
 * A styled composable that displays an informational message to the user.
 */
@Composable
fun InfoBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Customer information is used for rental agreements and communication purposes.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}



/**
 * A preview composable for visualizing the `AddCustomerScreen` in Android Studio's design pane.
 */
@Preview(showBackground = true)
@Composable
fun AddCustomerScreenPreview() {

    AddCustomerScreen(modifier = Modifier, viewModel())
}
