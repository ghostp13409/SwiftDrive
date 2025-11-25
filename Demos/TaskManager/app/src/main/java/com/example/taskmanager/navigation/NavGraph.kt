package com.example.taskmanager.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskmanager.features.AddTaskScreen
import com.example.taskmanager.features.HomeScreen
import com.example.taskmanager.features.ProfileScreen
import com.example.taskmanager.features.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("profile") { ProfileScreen() }
        composable("settings") { SettingsScreen() }
        composable("add_task") { AddTaskScreen() }
    }
}


