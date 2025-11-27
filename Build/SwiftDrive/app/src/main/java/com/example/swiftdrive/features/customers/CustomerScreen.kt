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
fun CustomerScreen(modifier: Modifier, viewModel: CustomerViewModel) {
    val customers = viewModel.customers

    val onEditCustomer: (Customer) -> Unit = { /* TODO: Handle customer edit */ }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
