package com.example.swiftdrive.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swiftdrive.features.cars.AddCarScreen
import com.example.swiftdrive.features.cars.CarScreen
import com.example.swiftdrive.features.customers.AddCustomerScreen
import com.example.swiftdrive.features.customers.CustomerScreen
import com.example.swiftdrive.features.home.HomeScreen
import com.example.swiftdrive.features.profile.ProfileScreen
import com.example.swiftdrive.features.rentals.AddRentalScreen
import com.example.swiftdrive.features.rentals.RentalScreen
import com.example.swiftdrive.features.splashscreen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {

            SplashScreen( onTimeout = {
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("home") { HomeScreen() }
        composable("cars") { CarScreen() }
        composable("add_car") { AddCarScreen() }
        composable("customer") { CustomerScreen() }
        composable("add_customer") { AddCustomerScreen() }
        composable("rentals") { RentalScreen() }
        composable("add_rental") { AddRentalScreen() }
        composable("profile") { ProfileScreen() }
    }
}
