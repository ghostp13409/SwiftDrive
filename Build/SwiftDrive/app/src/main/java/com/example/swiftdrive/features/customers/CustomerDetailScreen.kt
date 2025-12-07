package com.example.swiftdrive.features.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.components.customer.DetailRow

// Customer Detail Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    viewModel: CustomerViewModel,
    onBackClick: () -> Unit,
    onEditClick: (Customer) -> Unit
) {
    val customer = viewModel.selectedCustomer ?: return

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
                            text = "${customer.firstName} ${customer.lastName}",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            // --- Card Container ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {

                DetailRow("Customer ID", "#${customer.id}")
                DetailRow("Role", customer.roles.name)
                DetailRow("Age", customer.age.toString())
                DetailRow("Phone", customer.phoneNumber)
                DetailRow("Email", customer.email)
                DetailRow("Driving Licence", customer.drivingLicence)

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    // EDIT
                    IconButton(onClick = { onEditClick(customer) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    // DELETE
                    IconButton(onClick = {
                        viewModel.deleteCustomer(customer.id)
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}