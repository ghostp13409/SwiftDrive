package com.example.swiftdrive.navigation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import com.example.swiftdrive.components.navigation.BottomNavigationBar
import com.example.swiftdrive.components.navigation.FabButton
import com.example.swiftdrive.components.navigation.TopBar
import com.example.swiftdrive.features.cars.AddCarScreen
import com.example.swiftdrive.features.cars.CarDetailScreen
import com.example.swiftdrive.features.cars.CarScreen
import com.example.swiftdrive.features.customers.AddCustomerScreen
import com.example.swiftdrive.features.customers.CustomerDetailScreen
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
// The whole app navigation
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedGetBackStackEntry", "CoroutineCreationDuringComposition")
@Composable
fun AppNavigation (modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel : AppNavigationViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()  // Global homeViewModel for sync
    val carsViewModel: CarsViewModel = viewModel { CarsViewModel(context.applicationContext as Application, { homeViewModel.markAsChanged() }) }  // Global carsViewModel
    val rentalViewModel: RentalViewModel = viewModel { RentalViewModel(context.applicationContext as Application, { homeViewModel.markAsChanged(); carsViewModel.loadCars() }) }  // Global rentalViewModel
    val customerViewModel: CustomerViewModel = viewModel { CustomerViewModel(context.applicationContext as Application, { homeViewModel.markAsChanged() }) }  // Global customerViewModel
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    var currentTitle by remember { mutableStateOf<String>("SwiftDrive") }  // Default title
    var currentSubtext by remember { mutableStateOf<String>("Drive Fast. Drive Safe.") }  // Default subtext
    var onSyncClick by remember { mutableStateOf<(() -> Unit)?>(null) }  // Sync callback
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Show snackbar when sync success
    if (homeViewModel.showSyncSuccess) {
        scope.launch {
            snackbarHostState.showSnackbar("Sync completed successfully!")
            homeViewModel.dismissSyncSuccess()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if(viewModel.allowBottomTopBar.contains(currentRoute)){
                TopBar(
                    title = currentTitle,
                    subText = currentSubtext,
                    onSyncClick = onSyncClick,
                    isSyncing = homeViewModel.isSyncing,
                    hasUnsyncedChanges = homeViewModel.hasUnsyncedChanges
                )
            }
        },
        bottomBar = {
            // NOTE: Add More if needed to hide bottom bar on other screens (eg. login, signup)
            if(viewModel.allowBottomTopBar.contains(currentRoute)){
                BottomNavigationBar(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        },
        floatingActionButton = {
            if(viewModel.allowFab.contains(currentRoute)) {
                FabButton(onAddClick = {
                    navController.navigate(viewModel.getFabRoute())
                })
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                // Splash Screen
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
                // Login Screen
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
                // Registration Screen
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
                // Home Screen
            composable("home") {
                val homeViewModel: HomeViewModel = viewModel()
                // Change Title and SubText on TopBar
                currentTitle = "Dashboard"
                currentSubtext = "Drive Fast. Drive Safe."
                onSyncClick = { homeViewModel.syncAllToFirestore() }
                HomeScreen(modifier = Modifier, viewModel = homeViewModel, navController = navController )
            }
                // Cars Screen
            composable("cars") {
                currentTitle = "Cars"
                currentSubtext = "Find your next ride"
                onSyncClick = { homeViewModel.syncAllToFirestore() }
                CarScreen(
                    modifier = Modifier, viewModel = carsViewModel,
                    onEventClick = { navController.navigate("cars_detail") }
                )
            }
                // Add Car Screen
            composable("add_car") {
                AddCarScreen(
                    onEventClick = { navController.navigate("cars"){
                        popUpTo("cars"){
                            inclusive = true
                            saveState = true
                        }
                    } },
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier,
                    viewModel = carsViewModel,
                )
            }
                // Customer Screen
            composable("customer") {
                Log.d("AppNavigation", "Navigated to CustomerScreen")
                currentTitle = "Customers"
                currentSubtext = "${customerViewModel.customers.size} total customers"
                onSyncClick = { homeViewModel.syncAllToFirestore() }
                CustomerScreen(modifier = Modifier, viewModel = customerViewModel, onEdit = { navController.navigate("customer_detail") })
            }
                // Add Customer Screen
            composable("add_customer") {
                AddCustomerScreen(
                    modifier = Modifier,
                    viewModel = customerViewModel,
                    onSaveClick = {
                        navController.navigate("customer") {
                            popUpTo("customer") {
                                inclusive = true
                                saveState = true
                            }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
                // Car Detail Screen
                composable("cars_detail"){
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
                            rentalViewModel.updateSelectedCar(it)
                            navController.navigate("add_rental")
                        }
                    )
                }
                // Customer Detail Screen
            composable("customer_detail") {
                CustomerDetailScreen(
                    viewModel = customerViewModel,
                    onBackClick = {
                        navController.navigate("customer") {
                            popUpTo("customer") {
                                inclusive = true
                                saveState = true
                            }
                        }
                    },
                    onEditClick = {
                        customerViewModel.selectCustomer(it)
                        navController.navigate("add_customer") {
                        }
                    }
                )
            }
                // Rentals Screen
            composable("rentals") {
                currentTitle = "Rentals"
                currentSubtext = "${rentalViewModel.rentals.value.size} total rentals"
                onSyncClick = { homeViewModel.syncAllToFirestore() }
                RentalsScreen(modifier = Modifier, viewModel = rentalViewModel)
            }
                // Add Rental Screen
            composable("add_rental") {
                AddRentalScreen(modifier = Modifier, viewModel = rentalViewModel, onSaveClick = {navController.navigate("rentals")}, onBackClick = { navController.popBackStack() })
            }

                // Profile Screen
            composable ("profile") {
                val profileViewModel: ProfileViewModel = viewModel { ProfileViewModel(context.applicationContext as Application) }
                currentTitle = "Profile"
                currentSubtext = "View your profile"
                ProfileScreen(
                    viewModel = profileViewModel,
                    onEditProfile = {
                        navController.navigate("edit_profile")
                    },
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                )
            }

                // Edit Profile Screen
            composable("edit_profile") {
                val profileViewModel: ProfileViewModel = viewModel { ProfileViewModel(context.applicationContext as Application) }
                val customerViewModel: CustomerViewModel = viewModel { CustomerViewModel(context.applicationContext as Application, { profileViewModel.refreshData() }) }
                profileViewModel.currentUser?.let { user ->
                    customerViewModel.selectCustomer(user)
                }
                AddCustomerScreen(
                    modifier = Modifier,
                    viewModel = customerViewModel,
                    onSaveClick = {
                        navController.navigate("profile") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
            }
        }
    }
}