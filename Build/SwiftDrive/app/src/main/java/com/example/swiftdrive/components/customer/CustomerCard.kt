package com.example.swiftdrive.components.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.UserRoles

@Composable
fun CustomerCard(customer: Customer, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top part: Avatar, Name, ID, and Edit button
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = "${customer.firstName.firstOrNull() ?: '?'}${customer.lastName.firstOrNull() ?: '?'}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Name and ID column
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${customer.firstName} ${customer.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Customer ID: #${customer.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Info button
                IconButton(onClick = onEdit, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Customer Details",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider line
            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom part: Contact info
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoRow(icon = Icons.Default.Email, text = customer.email)
                InfoRow(icon = Icons.Default.Call, text = customer.phoneNumber)
            }
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CustomerCardPreview() {
    val sampleCustomer = Customer(
        id = 1,
        roles = UserRoles.USER,
        firstName = "John",
        lastName = "Doe",
        age = 30,
        phoneNumber = "(555) 123-4567",
        drivingLicence = "A123B456",
        email = "john.doe@email.com",
        password = "password"
    )
    CustomerCard(customer = sampleCustomer, onEdit = {})
}
