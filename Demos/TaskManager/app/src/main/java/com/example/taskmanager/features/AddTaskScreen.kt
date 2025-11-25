package com.example.taskmanager.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Task",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Add New Task",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text("Task Name") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Task Description") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Priority (High, Medium, Low)") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save Task")
        }
    }
}