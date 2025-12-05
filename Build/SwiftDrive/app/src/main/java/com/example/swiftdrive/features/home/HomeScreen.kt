package com.example.swiftdrive.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.swiftdrive.navigation.AppNavigationViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchAllFromFirestore()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Add scroll to the Column
            .padding(16.dp)
            .padding(bottom = 80.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(), // Remove verticalScroll from here
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ManageCard(
                    count = "24",
                    label = "Total Cars",
                    icon = Icons.Outlined.DirectionsCar,
                    iconBackgroundColor = Color(0xFFD4C4A8),
                    iconTint = Color(0xFF8B7355)
                )
            }
            item {
                ManageCard(
                    count = "8",
                    label = "Active Rentals",
                    icon = Icons.Outlined.CalendarToday,
                    iconBackgroundColor = Color(0xFFD1D5DB),
                    iconTint = Color(0xFF6B7280)
                )
            }
            item {
                ManageCard(
                    count = "16",
                    label = "Available",
                    icon = Icons.Outlined.DirectionsCar,
                    iconBackgroundColor = Color(0xFFD1FAE5),
                    iconTint = Color(0xFF10B981)
                )
            }
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
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        QuickActionButton(
            title = "New Rental",
            icon = Icons.Outlined.CalendarToday,
            iconBackgroundColor = Color(0xFFD4C4A8),
            iconTint = Color(0xFF8B7355),
            onClick = {
                // TODO: Navigate to New Rental screen
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        QuickActionButton(
            title = "View Rentals",
            icon = Icons.Outlined.Description,
            iconBackgroundColor = Color(0xFFE5E7EB),
            iconTint = Color(0xFF6B7280),
            onClick = {
                // TODO: Navigate to View Rentals screen
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        QuickActionButton(
            title = "Seed Data",
            icon = Icons.Outlined.CloudDownload,
            iconBackgroundColor = Color(0xFFD1FAE5),
            iconTint = Color(0xFF10B981),
            onClick = {
                viewModel.seedData()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Recent Activities",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        RecentActivitySection()
    }
}

