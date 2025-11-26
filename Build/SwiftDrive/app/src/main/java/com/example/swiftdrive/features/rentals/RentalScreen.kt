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
import com.example.swiftdrive.components.RentalCard
import com.example.swiftdrive.components.TabButton

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
                .padding()
        )  {
                Row(
                    modifier = modifier
                        .padding(16.dp)
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
                    contentPadding = PaddingValues(16.dp),
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


