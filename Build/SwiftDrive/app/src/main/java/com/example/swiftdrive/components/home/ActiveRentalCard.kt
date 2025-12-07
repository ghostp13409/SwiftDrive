package com.example.swiftdrive.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiftdrive.features.home.HomeViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Active Rental Card Component for displaying active rental information in a card view
@Composable
fun ActiveRentalCard(viewModel: HomeViewModel) {
    val rental = viewModel.currentUserActiveRental ?: return
    val car = viewModel.getCarbyId(rental.carId)
    val customer = viewModel.getCustomerById(rental.userId)

    // Format dates
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    val endDate =
            try {
                LocalDate.parse(rental.rentalEnd).format(formatter)
            } catch (e: Exception) {
                rental.rentalEnd
            }

    // The Card for displaying the rental information
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
            ) {
                // Car information
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                            text = car?.let { "${it.year} ${it.make} ${it.model}" }
                                            ?: "Unknown Car",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                                text = "${customer?.firstName} ${customer?.lastName}",
                                fontSize = 12.sp,
                        )
                    }
                }

                // Rental status
                Surface(color = Color(0xFFD1FAE5), shape = RoundedCornerShape(8.dp)) {
                    Text(
                            text = "Active",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF059669),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                        text = "Return by $endDate",
                        fontSize = 12.sp,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Total cost
            Text(
                    text = "$ ${rental.totalCost.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
