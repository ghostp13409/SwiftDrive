package com.example.swiftdrive.components.customer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.Customer

// Customer List Component for displaying a list of customers
@Composable
fun CustomerList(customers: List<Customer>, onEdit: (Customer) -> Unit, modifier: Modifier) {
    // Lazy Column to display the list of customers
    LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
    ) {
        items(customers, key = { it.id }) { customer ->
            CustomerCard(customer = customer, onEdit = { onEdit(customer) })
        }
    }
}
