package com.example.eventexplorer8959999.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventexplorer8959999.features.eventdetail.EventDetailScreen
import com.example.eventexplorer8959999.features.eventlist.EventListScreen
import com.example.eventexplorer8959999.features.eventlist.EventListViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AppNavigation(navController: NavController, modifier: Modifier = Modifier){

    // Routes
    NavHost(
        navController = navController as androidx.navigation.NavHostController,
        startDestination = "event_list", // Default Route
        modifier = modifier
    ) {
        // Event List Screen Route
        composable("event_list"){
            val viewModel: EventListViewModel = viewModel()
            EventListScreen(
                viewModel = viewModel,
                onEventClick = {
                    navController.navigate("event_detail")
                }
            )
        }

        // Event Detail Screen Route
        composable("event_detail") {
            // Get ViewModel from backstack entry of event_list screen
            val eventListStackEntry = remember { navController.getBackStackEntry("event_list") }
            val eventListViewModel: EventListViewModel = viewModel(eventListStackEntry)
            EventDetailScreen(
                viewModel = eventListViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}