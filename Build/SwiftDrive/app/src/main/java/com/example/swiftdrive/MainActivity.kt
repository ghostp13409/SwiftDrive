package com.example.swiftdrive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.swiftdrive.navigation.AppNavigation
import com.example.swiftdrive.ui.theme.SwiftDriveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwiftDriveTheme {
                AppNavigation()
            }
        }
    }
}