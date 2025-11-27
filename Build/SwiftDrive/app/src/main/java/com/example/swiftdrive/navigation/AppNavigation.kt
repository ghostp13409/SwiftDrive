package com.example.swiftdrive.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swiftdrive.components.BottomNavigationBar
import com.example.swiftdrive.components.FabButton
import com.example.swiftdrive.components.TopBar
import com.example.swiftdrive.features.cars.AddCarScreen
import com.example.swiftdrive.features.cars.CarScreen
import com.example.swiftdrive.features.customers.AddCustomerScreen
import com.example.swiftdrive.features.customers.CustomerScreen
import com.example.swiftdrive.features.home.HomeScreen
import com.example.swiftdrive.features.home.HomeViewModel
import com.example.swiftdrive.features.rentals.AddRentalScreen
import com.example.swiftdrive.features.rentals.RentalScreen
import com.example.swiftdrive.features.splashscreen.SplashScreen
import com.example.swiftdrive.features.cars.CarsViewModel
import com.example.swiftdrive.features.customers.CustomerViewModel
import com.example.swiftdrive.features.rentals.RentalViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation () {
    val viewModel : AppNavigationViewModel = viewModel()
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    var currentTitle by remember { mutableStateOf<String>("SwiftDrive") }  // Default title
    var currentSubtext by remember { mutableStateOf<String>("Drive Fast. Drive Safe.") }  // Default subtext

    Scaffold(
        topBar = {
            if(currentRoute != "splash"){
                TopBar(
                    title = currentTitle,
                    subText = currentSubtext
                )
            }
        },
        bottomBar = {
            // NOTE: Add More if needed to hide bottom bar on other screens (eg. login, signup)
            if(currentRoute != "splash"){
                BottomNavigationBar(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        },
        floatingActionButton = {
            if(viewModel.showFab) {
                FabButton(onAddClick = {
                    navController.navigate(viewModel.getFabRoute())
                })
            }
        }
    ) { innerPadding ->
        NavGraph(navController = navController, innerPadding = innerPadding)
    }
}