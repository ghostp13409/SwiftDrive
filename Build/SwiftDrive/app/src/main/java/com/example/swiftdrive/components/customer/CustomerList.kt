package com.example.swiftdrive.components.customer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.Customer

@Composable
fun CustomerList(customers: List<Customer>, onEdit: (Customer) -> Unit){
    LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(customers, key = {it.id}){customer->
            CustomerCard(
                customer = customer,
                onEdit = {onEdit(customer)})
        }
    }
}
