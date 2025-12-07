package com.example.swiftdrive.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Floating Action Button Component for adding new tasks
@Composable
fun FabButton(onAddClick: () -> Unit) {
    FloatingActionButton(
            onClick = onAddClick,
            shape = CircleShape,
            modifier = Modifier.padding(16.dp)
    ) { Icon(Icons.Default.Add, contentDescription = "Add Task") }
}
