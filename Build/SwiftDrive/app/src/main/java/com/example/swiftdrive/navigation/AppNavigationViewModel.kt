package com.example.swiftdrive.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CarRental
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// App Navigation View Model
class AppNavigationViewModel : ViewModel() {

    // Navigation Items
    val items = listOf("home", "cars", "customer", "rentals", "profile")

    var selectedItem by mutableStateOf(items[0])
        private set
    val icons =
            listOf(
                    Icons.Outlined.Home,
                    Icons.Outlined.DirectionsCar,
                    Icons.Outlined.Group,
                    Icons.Outlined.CarRental,
                    Icons.Outlined.Person
            )
    val iconsSelected =
            listOf(
                    Icons.Filled.Home,
                    Icons.Filled.DirectionsCar,
                    Icons.Filled.Group,
                    Icons.Filled.CarRental,
                    Icons.Filled.Person
            )
    // Bottom Navigation Bar
    val labels = listOf("Home", "Cars", "Customers", "Rentals", "Profile")

    val allowFab = listOf("cars", "customer", "rentals")

    val allowBottomTopBar = listOf("home", "cars", "customer", "rentals", "profile")

    var showFab by mutableStateOf(false)
        private set

    // Setters
    fun updateSelectedItem(item: String) {
        selectedItem = item
    }

    fun handleButtonClick(item: String) {
        // update selected route
        selectedItem = item
    }

    fun getFabRoute(): String {
        return when (selectedItem) {
            "cars" -> "add_car"
            "customer" -> "add_customer"
            "rentals" -> "add_rental"
            else -> ""
        }
    }
}
