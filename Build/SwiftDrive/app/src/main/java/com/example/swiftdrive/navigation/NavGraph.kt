package com.example.swiftdrive.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swiftdrive.features.cars.AddCarScreen
import com.example.swiftdrive.features.cars.CarScreen
import com.example.swiftdrive.features.cars.CarsViewModel
import com.example.swiftdrive.features.customers.AddCustomerScreen
import com.example.swiftdrive.features.customers.CustomerScreen
import com.example.swiftdrive.features.customers.CustomerViewModel
import com.example.swiftdrive.features.home.HomeScreen
import com.example.swiftdrive.features.home.HomeViewModel
import com.example.swiftdrive.features.profile.ProfileScreen
import com.example.swiftdrive.features.profile.ProfileViewModel
import com.example.swiftdrive.features.rentals.AddRentalScreen
import com.example.swiftdrive.features.rentals.RentalScreen
import com.example.swiftdrive.features.rentals.RentalViewModel
import com.example.swiftdrive.features.splashscreen.SplashScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {

            SplashScreen(onTimeout = {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(modifier = Modifier.padding(innerPadding), viewModel = homeViewModel )
        }
        composable("cars") {
            val carsViewModel: CarsViewModel = viewModel()
            CarScreen(
                modifier = Modifier.padding(innerPadding), viewModel = carsViewModel,
                onEventClick = { TODO() }
            )
        }
        composable("add_car") {
            // Get ViewModel from backstack entry of cars screen
            val carsStackEntry = remember { navController.getBackStackEntry("cars") }
            val carViewModel: CarsViewModel = viewModel(carsStackEntry)
            AddCarScreen(
                modifier = Modifier.padding(innerPadding),
                viewModel = carViewModel,
            )
        }
        composable("customer") {
            val customerViewModel: CustomerViewModel = viewModel()
            CustomerScreen(modifier = Modifier.padding(innerPadding), viewModel = customerViewModel)
        }
        composable("add_customer") {
            // Get ViewModel from backstack entry of event_list screen
            val customerStackEntry = remember { navController.getBackStackEntry("customer") }
            val customerViewModel: CustomerViewModel = viewModel(customerStackEntry)
            AddCustomerScreen(modifier = Modifier.padding(innerPadding), viewModel = customerViewModel)
        }
        composable("rentals") {
            val rentalViewModel: RentalViewModel = viewModel()
            RentalScreen(modifier = Modifier.padding(innerPadding), viewModel = rentalViewModel)
        }
        composable("add_rental") {
            // Get ViewModel from backstack entry of event_list screen
            val rentalsStackEntry = remember { navController.getBackStackEntry("rentals") }
            val rentalViewModel: RentalViewModel = viewModel(rentalsStackEntry)
            AddRentalScreen(modifier = Modifier.padding(innerPadding), viewModel = rentalViewModel)
        }

        composable ("profile") {
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(modifier = Modifier.padding(innerPadding), viewModel = profileViewModel)
        }
    }
}
