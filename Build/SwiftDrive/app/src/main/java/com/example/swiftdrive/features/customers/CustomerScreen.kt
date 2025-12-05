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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.components.customer.CustomerList
import com.example.swiftdrive.data.models.Customer

@Composable
fun CustomerScreen(modifier: Modifier, viewModel: CustomerViewModel, onEdit: (Customer) -> Unit = {}) {
    // In a real app, you would get this list from a ViewModel
//    val customers = remember {
//        listOf(
//            Customer(1, UserRoles.USER, "John", "Doe", 30, "(555) 123-4567", "JD123", "john.doe@email.com", "pass"),
//            Customer(2, UserRoles.USER, "Sarah", "Smith", 28, "(555) 234-5678", "SS123", "sarah.smith@email.com", "pass"),
//            Customer(3, UserRoles.USER, "Michael", "Johnson", 45, "(555) 345-6789", "MJ123", "m.johnson@email.com", "pass"),
//            Customer(4, UserRoles.USER, "Emily", "Davis", 35, "(555) 456-7890", "ED123", "emily.d@email.com", "pass")
//        )
//    }

    // In a real app, the onEdit lambda would navigate to an edit screen or show a dialog
    val onEditCustomer: (Customer) -> Unit = { customer ->
        viewModel.selectCustomer(customer)
        onEdit(customer)
    }
    val customers = viewModel.customers


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --- Customer List ---
        CustomerList(
            customers = customers,
            onEdit = onEditCustomer,
            modifier = Modifier
        )

        if(customers.isEmpty()){
            Text(
                text = "No customers found",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CustomerScreenPreview() {

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CustomerScreen(modifier = Modifier, viewModel = CustomerViewModel(application = Application()))
        }
    }
}
