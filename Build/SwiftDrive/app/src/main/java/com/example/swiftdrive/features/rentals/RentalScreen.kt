package com.example.swiftdrive.features.rentals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiftdrive.components.rental.RentalCard
import com.example.swiftdrive.components.rental.TabButton

// Rentals Screen for Rentals Page
@Composable
fun RentalsScreen(
    viewModel: RentalViewModel,
    modifier: Modifier.Companion
) {
    var selectedTab by remember { mutableStateOf(0) }
    val rentals by viewModel.rentals
    
    // Filter rentals by status
    val activeRentals = rentals.filter { it.status.equals("Active", ignoreCase = true) }
    val historyRentals = rentals.filter { !it.status.equals("Active", ignoreCase = true) }
    

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        )  {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TabButton(
                        text = "Active (${activeRentals.size})",
                        isSelected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        modifier = modifier.weight(1f)
                    )
                    TabButton(
                        text = "History (${historyRentals.size})",
                        isSelected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        modifier = modifier.weight(1f)
                    )
                }

            Spacer(modifier = modifier.height(16.dp))
            
            // Rental Cards
            val displayRentals = if (selectedTab == 0) activeRentals else historyRentals
            
            if (displayRentals.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No ${if (selectedTab == 0) "active" else "history"} rentals",
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(displayRentals) { rental ->
                        RentalCard(
                            rental = rental,
                            viewModel = viewModel,
                            onReturnCar = {
                                viewModel.returnCar(rental.id)
                            }
                        )
                    }
                }
            }
        }
}


