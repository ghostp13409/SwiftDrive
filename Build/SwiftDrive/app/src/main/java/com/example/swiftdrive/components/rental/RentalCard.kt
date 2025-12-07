package com.example.swiftdrive.components.rental

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
import com.example.swiftdrive.data.models.Rental
import com.example.swiftdrive.features.rentals.RentalViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Rental Card component for displaying rental information in a card view
@Composable
fun RentalCard(
    rental: Rental,
    viewModel: RentalViewModel,
    onReturnCar: () -> Unit
) {
    // Get car and customer details
    val car = viewModel.cars.value.find { it.id == rental.carId }
    val customer = viewModel.customers.value.find { it.id == rental.userId }

    // Format dates
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    val startDate = try {
        LocalDate.parse(rental.rentalStart).format(formatter)
    } catch (e: Exception) {
        rental.rentalStart
    }
    val endDate = try {
        LocalDate.parse(rental.rentalEnd).format(formatter)
    } catch (e: Exception) {
        rental.rentalEnd
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = car?.let { "${it.year} ${it.make} ${it.model}" } ?: "Unknown Car",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
//                            Add Tint
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${customer?.firstName} ${customer?.lastName}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dates
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Start Date",
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF9CA3AF)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = startDate,
                            fontSize = 14.sp,
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Return Date",
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFF9CA3AF)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = endDate,
                            fontSize = 14.sp,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price and Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Rental Status
                Surface(
                    color = if (rental.status.equals("Active", ignoreCase = true))
                        Color(0xFFD1FAE5) else Color(0xFFE5E7EB),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = rental.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (rental.status.equals("Active", ignoreCase = true))
                            Color(0xFF059669) else Color(0xFF6B7280),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (rental.status.equals("Active", ignoreCase = true)) {
                    Button(
                        onClick = onReturnCar,
                        colors = ButtonDefaults.buttonColors(
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Return Car",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Text(
                    text = "$ ${rental.totalCost.toInt()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}