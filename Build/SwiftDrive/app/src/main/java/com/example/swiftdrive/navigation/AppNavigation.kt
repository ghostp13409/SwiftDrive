package com.example.swiftdrive.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.example.swiftdrive.components.BottomNavigationBar
import com.example.swiftdrive.components.FabButton

@Composable
fun AppNavigation () {
    val viewModel : AppNavigationViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                viewModel = viewModel
            )
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