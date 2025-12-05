package com.example.swiftdrive.components.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiftdrive.data.models.Car

@Composable
fun AvailabilityAndPriceSection(
    car: Car,
    onBookClicked: (Car) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "$${"%.2f".format(car.pricePerDay)} / day",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        val isAvailable = car.isAvailable
        val availabilityText = if (isAvailable) "Available" else "Not Available"
        val availabilityColor =
            if (isAvailable) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error

        Surface(
            color = availabilityColor.copy(alpha = 0.15f),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = availabilityText,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
                color = availabilityColor,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        //-----------BOOK NOW BUTTON-----------
        Button(
            onClick = { onBookClicked(car) },
            enabled = isAvailable,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(50.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Book Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}