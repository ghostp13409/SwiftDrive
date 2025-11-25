package com.example.drawerdemo.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drawerdemo.features.home.HomeScreen
import com.example.drawerdemo.features.profile.ProfileScreen
import com.example.drawerdemo.features.settings.SettingsScreen

import com.example.drawerdemo.ui.theme.DrawerDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val viewModel: AppNavigationViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = viewModel.drawerState)
    val scope = rememberCoroutineScope()
    val selectedScreen = viewModel.selectedScreen

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerItem("Home", selectedScreen, scope, drawerState) { viewModel.onScreenSelected("Home") }
                DrawerItem("Profile", selectedScreen, scope, drawerState) { viewModel.onScreenSelected("Profile") }
                DrawerItem("Settings", selectedScreen, scope, drawerState) { viewModel.onScreenSelected("Settings") }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(selectedScreen) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        )
        { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedScreen) {
                    "Home" -> HomeScreen()
                    "Profile" -> ProfileScreen()
                    "Settings" -> SettingsScreen()
                }
            }
        }

    }
}

@Composable
fun DrawerItem(
    title: String,
    selected: String,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onClick: () -> Unit
)

{
    val isSelected = selected == title
    val background = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    else Color.Transparent

    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
                scope.launch { drawerState.close() }
            }
            .background(background)
            .padding(16.dp)
    )
}




@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    DrawerDemoTheme {
        AppNavigation()
    }
}



