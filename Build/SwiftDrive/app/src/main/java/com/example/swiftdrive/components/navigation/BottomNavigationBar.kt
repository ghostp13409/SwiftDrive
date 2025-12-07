package com.example.swiftdrive.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swiftdrive.navigation.AppNavigationViewModel

// Bottom Navigation Bar Component for displaying navigation options in a bottom bar
@Composable
fun BottomNavigationBar(navController: NavController, viewModel: AppNavigationViewModel) {
    // Navigation Bar for displaying navigation options
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route
        viewModel.items.forEachIndexed { index, item ->
            val icon = (currentRoute == item).let {
                if (it) viewModel.iconsSelected[index] else viewModel.icons[index]
            }
            // Navigation Item for each option
            NavigationBarItem(
                selected = currentRoute == item,
                onClick = {
                    viewModel.handleButtonClick(item)
                    navController.navigate(viewModel.selectedItem)
                },
                icon = { Icon(icon, contentDescription = viewModel.labels[index]) },
                label = { Text(viewModel.labels[index]) }
            )
        }
    }
}