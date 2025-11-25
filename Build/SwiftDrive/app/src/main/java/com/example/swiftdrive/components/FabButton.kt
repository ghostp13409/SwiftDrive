package com.example.swiftdrive.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

// TODO: Change the Color Scheme

@Composable
fun FabButton(onAddClick: () -> Unit) {
    FloatingActionButton(onClick = onAddClick) {
        Icon(Icons.Default.Add, contentDescription = "Add Task")
    }
}