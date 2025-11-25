package com.example.drawerdemo.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppNavigationViewModel: ViewModel() {

    var drawerState = DrawerValue.Closed
        private set

    var selectedScreen by mutableStateOf("Home")
        private set

    fun onScreenSelected(screen: String) {
        selectedScreen = screen
    }
}

