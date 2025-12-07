package com.example.swiftdrive.features.customers

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.R
import com.example.swiftdrive.components.customer.CustomerList
import com.example.swiftdrive.data.models.Customer

// Customer Screen For
@Composable
fun CustomerScreen(
        modifier: Modifier,
        viewModel: CustomerViewModel,
        onEdit: (Customer) -> Unit = {}
) {
    // Lambda for editing a customer
    val onEditCustomer: (Customer) -> Unit = { customer ->
        viewModel.selectCustomer(customer)
        onEdit(customer)
    }
    // Get all customers
    val customers = viewModel.customers

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        // --- Customer List ---
        CustomerList(customers = customers, onEdit = onEditCustomer, modifier = Modifier)
        // Checks to see if there are any customers
        if (customers.isEmpty()) {
            Text(
                    text = stringResource(R.string.no_customers_found),
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
            CustomerScreen(
                    modifier = Modifier,
                    viewModel = CustomerViewModel(application = Application())
            )
        }
    }
}
