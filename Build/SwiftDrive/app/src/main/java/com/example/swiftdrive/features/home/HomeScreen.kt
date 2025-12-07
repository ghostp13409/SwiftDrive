package com.example.swiftdrive.features.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.swiftdrive.R
import com.example.swiftdrive.components.home.ActiveRentalCard
import com.example.swiftdrive.components.home.ManageCard
import com.example.swiftdrive.components.home.QuickActionButton

// Home Screen for Home Page
@Composable
fun HomeScreen(
        modifier: Modifier = Modifier,
        viewModel: HomeViewModel,
        navController: NavController
) {
    // Fetch data only once
    LaunchedEffect(Unit) {
        if (!viewModel.isFetched) {
            viewModel.updateCounts()
            viewModel.updateIsFetched(true)
        }
    }
    // This will run when the composable is disposed
    DisposableEffect(Unit) { onDispose { viewModel.updateIsFetched(false) } }

    // Main Column
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ManageCard(
                    count = viewModel.totalCars.toString(),
                    label = "Total Cars",
                    icon = Icons.Outlined.DirectionsCar,
                    iconBackgroundColor = Color(0xFFD4C4A8),
                    iconTint = Color(0xFF8B7355)
            )
            ManageCard(
                    count = viewModel.activeRentals.toString(),
                    label = "Active",
                    icon = Icons.Outlined.CalendarToday,
                    iconBackgroundColor = Color(0xFFD1D5DB),
                    iconTint = Color(0xFF6B7280)
            )

            ManageCard(
                    count = viewModel.availableCars.toString(),
                    label = "Available",
                    icon = Icons.Outlined.DirectionsCar,
                    iconBackgroundColor = Color(0xFFD1FAE5),
                    iconTint = Color(0xFF10B981)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
                "Quick Actions",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Action Buttons
        QuickActionButton(
                title = "Add New Car",
                icon = Icons.Default.Add,
                iconBackgroundColor = Color(0xFF1F4529),
                iconTint = Color.White,
                onClick = { navController.navigate("add_car") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        QuickActionButton(
                title = "New Rental",
                icon = Icons.Outlined.CalendarToday,
                iconBackgroundColor = Color(0xFFD4C4A8),
                iconTint = Color(0xFF8B7355),
                onClick = { navController.navigate("add_rental") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        QuickActionButton(
                title = "View Rentals",
                icon = Icons.Outlined.Description,
                iconBackgroundColor = Color(0xFFE5E7EB),
                iconTint = Color(0xFF6B7280),
                onClick = { navController.navigate("rentals") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
                stringResource(R.string.active_rental),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Active Rental Card
        ActiveRentalCard(viewModel = viewModel)
    }
}
