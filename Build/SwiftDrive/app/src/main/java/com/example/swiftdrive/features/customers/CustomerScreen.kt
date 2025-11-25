package com.example.swiftdrive.features.customers

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.components.customer.CustomerList
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.UserRoles

@Composable
fun CustomerScreen(modifier: Modifier, viewModel: CustomerViewModel) {
    // In a real app, you would get this list from a ViewModel
    val customers = remember {
        listOf(
            Customer(1, UserRoles.USER, "John", "Doe", 30, "(555) 123-4567", "JD123", "john.doe@email.com", "pass"),
            Customer(2, UserRoles.USER, "Sarah", "Smith", 28, "(555) 234-5678", "SS123", "sarah.smith@email.com", "pass"),
            Customer(3, UserRoles.USER, "Michael", "Johnson", 45, "(555) 345-6789", "MJ123", "m.johnson@email.com", "pass"),
            Customer(4, UserRoles.USER, "Emily", "Davis", 35, "(555) 456-7890", "ED123", "emily.d@email.com", "pass")
        )
    }

    // In a real app, the onEdit lambda would navigate to an edit screen or show a dialog
    val onEditCustomer: (Customer) -> Unit = { /* TODO: Handle customer edit */ }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Screen Header ---
        Text(
            text = "Customers",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${customers.size} total customers",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Customer List ---
        CustomerList(
            customers = customers,
            onEdit = onEditCustomer
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CustomerScreenPreview() {
    // Using a Scaffold to mimic a real screen with a background color
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CustomerScreen(modifier = Modifier, viewModel = CustomerViewModel(application = Application()))
        }
    }
}
