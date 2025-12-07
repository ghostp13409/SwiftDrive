package com.example.swiftdrive.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Top Bar Component for displaying the top bar of the app
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, subText: String, onSyncClick: (() -> Unit)? = null, isSyncing: Boolean = false, hasUnsyncedChanges: Boolean = false) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        // the title parameter is used to display the title of the app
        title = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        // the actions parameter is used to display the actions of the app
        actions = {
            if (onSyncClick != null && hasUnsyncedChanges) {
                IconButton(onClick = onSyncClick) {
                    if (isSyncing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sync",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        windowInsets = TopAppBarDefaults.windowInsets



    )
}