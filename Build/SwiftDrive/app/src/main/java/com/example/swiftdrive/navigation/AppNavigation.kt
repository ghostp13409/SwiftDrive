package com.example.swiftdrive.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import com.example.swiftdrive.components.BottomNavigationBar
import com.example.swiftdrive.components.FabButton
import com.example.swiftdrive.components.TopBar
import com.example.swiftdrive.features.cars.AddCarScreen
import com.example.swiftdrive.features.cars.CarDetailScreen
import com.example.swiftdrive.features.cars.CarScreen
import com.example.swiftdrive.features.customers.AddCustomerScreen
import com.example.swiftdrive.features.customers.CustomerScreen
import com.example.swiftdrive.features.home.HomeScreen
import com.example.swiftdrive.features.home.HomeViewModel
import com.example.swiftdrive.features.login.LoginScreen
import com.example.swiftdrive.features.login.LoginViewModel
import com.example.swiftdrive.features.rentals.AddRentalScreen
import com.example.swiftdrive.features.signup.RegistrationScreen
import com.example.swiftdrive.features.signup.RegistrationViewModel
import com.example.swiftdrive.features.splashscreen.SplashScreen
import com.example.swiftdrive.features.cars.CarsViewModel
import com.example.swiftdrive.features.customers.CustomerViewModel
import com.example.swiftdrive.features.profile.ProfileScreen
import com.example.swiftdrive.features.profile.ProfileViewModel
import com.example.swiftdrive.features.rentals.RentalViewModel
import com.example.swiftdrive.features.rentals.RentalsScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation (modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel : AppNavigationViewModel = viewModel()
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    var currentTitle by remember { mutableStateOf<String>("SwiftDrive") }  // Default title
    var currentSubtext by remember { mutableStateOf<String>("Drive Fast. Drive Safe.") }  // Default subtext

    Scaffold(
        modifier = modifier,
        topBar = {
            if(currentRoute != "splash" && currentRoute != "login" && currentRoute != "register"){
                TopBar(
                    title = currentTitle,
                    subText = currentSubtext
                )
            }
        },
        bottomBar = {
            // NOTE: Add More if needed to hide bottom bar on other screens (eg. login, signup)
            if(currentRoute != "splash" && currentRoute != "login" && currentRoute != "register"){
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier.fillMaxSize(),
            ) {
            composable("splash") {
                SplashScreen(onTimeout = {
                    if (sessionManager.isLoggedIn()) {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    } else {
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                })
            }
            composable("login") {
                val loginViewModel: LoginViewModel = viewModel()
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("register")
                    }
                )
            }
            composable("register") {
                val registrationViewModel: RegistrationViewModel = viewModel()
                RegistrationScreen(
                    viewModel = registrationViewModel,
                    onRegistrationSuccess = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                val homeViewModel: HomeViewModel = viewModel()
                // Change Title and SubText on TopBar
                currentTitle = "Dashboard"
                currentSubtext = "Drive Fast. Drive Safe."
                HomeScreen(modifier = Modifier, viewModel = homeViewModel )
            }
            composable("cars") {
                val carsViewModel: CarsViewModel = viewModel()
                currentTitle = "Cars"
                currentSubtext = "Find your next ride"
                CarScreen(
                    modifier = Modifier, viewModel = carsViewModel,
                    onEventClick = { navController.navigate("cars_detail") }
                )
            }
            composable("add_car") {
                // Get ViewModel from backstack entry of cars screen
                val carsStackEntry = remember { navController.getBackStackEntry("cars") }
                val carViewModel: CarsViewModel = viewModel(carsStackEntry)
                currentTitle = "Add Car"
                AddCarScreen(
                    onEventClick = { navController.navigate("cars"){
                        popUpTo("cars"){
                            inclusive = true
                            saveState = true
                        }
                    } },
                    modifier = Modifier,
                    viewModel = carViewModel,
                )
            }
            composable("customer") {
                Log.d("AppNavigation", "Navigated to CustomerScreen")
                val customerViewModel: CustomerViewModel = viewModel()
                currentTitle = "Customers"
                currentSubtext = "${customerViewModel.customers.size} total customers"
                CustomerScreen(modifier = Modifier, viewModel = customerViewModel)
            }
            composable("add_customer") {
                // Get ViewModel from backstack entry of event_list screen
                val customerStackEntry = remember { navController.getBackStackEntry("customer") }
                val customerViewModel: CustomerViewModel = viewModel(customerStackEntry)
                currentTitle = "Add Customer"
                AddCustomerScreen(modifier = Modifier, viewModel = customerViewModel)
            }
            composable("rentals") {
                val rentalViewModel: RentalViewModel = viewModel()
                currentTitle = "Rentals"
                currentSubtext = "${rentalViewModel.rentals.value.size} total rentals"
                RentalsScreen(modifier = Modifier, viewModel = rentalViewModel)
            }
            composable("add_rental/{carId}") {
                // Get ViewModel from backstack entry of event_list screen
                val rentalsStackEntry = remember { navController.getBackStackEntry("rentals") }
                val rentalViewModel: RentalViewModel = viewModel(rentalsStackEntry)
                val id = it.arguments?.getString("carId")!!.toInt()
                currentTitle = "Add Rental"
                AddRentalScreen(modifier = Modifier, viewModel = rentalViewModel,carId = id)
            }

            composable ("profile") {
                val profileViewModel: ProfileViewModel = viewModel()
                currentTitle = "Profile"
                currentSubtext = "View your profile"
                ProfileScreen(
                    modifier = Modifier,
                    viewModel = profileViewModel,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                )
            }


                composable("cars_detail"){
                    val carListBackStackEntry = remember { navController.getBackStackEntry("cars") }
                    val carsViewModel: CarsViewModel = viewModel(carListBackStackEntry)
                    CarDetailScreen(
                        viewModel = carsViewModel,
                        onBackClick = {
                            navController.navigate("cars") {
                                popUpTo("cars") {
                                    inclusive = true
                                    saveState = true
                                }
                            }
                        },
                        onEditClick = {
                            carsViewModel.selectCar(it)
                            navController.navigate("add_car") {
                            }
                        },
                        onBookClicked = {
                            carsViewModel.selectCar(it)

                            // Ensure rentals is in the back stack
                            navController.navigate("rentals") {
                                launchSingleTop = true
                            }
                            // Now navigate to add rental
                            navController.navigate("add_rental/${carsViewModel.selectedCar?.id}")
                        }
                    )
                }
            }
        }
    }
}