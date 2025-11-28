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
import com.example.swiftdrive.components.customer.RoleDropdown



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(modifier: Modifier, viewModel: CustomerViewModel) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Customer") },
                navigationIcon = {
                    // Parth help me with back navigation
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // Sets the colors for the TopAppBar to match the app's theme.
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        // The main layout column, constrained by the Scaffold's padding.
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Makes the content scrollable if it exceeds screen height.
        ) {
            // This inner Column holds the form fields and takes up available vertical space.
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Adds space between each child composable.
            ) {
                // --- Form Input Fields ---
                OutlinedTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.updateFirstName(it)  },
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
                    value =  viewModel.email,
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
                RoleDropdown(
                    selectedRole = viewModel.roles,
                    onRoleSelected = { viewModel.updateRoles(it) }
                )

                // --- Informational Box ---
                InfoBox()
            }

            // --- Action Buttons at the bottom ---
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Primary action button for saving the customer.
                Button(
                    onClick = {viewModel.saveCustomer()},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text("Save Customer", style = MaterialTheme.typography.bodyLarge)
                }
                // Secondary action button for cancelling the operation.
                OutlinedButton(
                    onClick = {viewModel.clearInput()},
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

