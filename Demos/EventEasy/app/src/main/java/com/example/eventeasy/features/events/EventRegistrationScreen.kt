package com.example.eventeasy.features.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventeasy.data.model.Event
import java.util.Calendar

@Composable
fun EventRestitrationScreen(modifier: Modifier = Modifier) {
    val viewModel : EventViewModel = viewModel()
    val events by viewModel.events.collectAsState()
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            "Event Registration",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel. name. collectAsState() . value,
            onValueChange = { viewModel.name. value = it },
            label = { Text( text = "Event Name") },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height( height=8.dp))

        OutlinedTextField(
            value = viewModel. description. collectAsState () .value,
            onValueChange = { viewModel. description. value = it },
            label = { Text ( text = "Description") },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height( height=8.dp))

        // Date Picker
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(
            context,
            {_, y, m, d ->
                viewModel.date.value = "$d/${m+1}/$y"
            },
            year, month, day
        )

        OutlinedTextField(
            value = viewModel.date.collectAsState().value,
            onValueChange = { },
            label = { Text(text = "Date") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {datePickerDialog.show()}) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            }
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.addEvent() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Event")
        }

        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(events) { event ->
                EventCard(event)
            }
        }
    }
}

@Composable
fun EventCard(event: Event) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            text = event.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Date: ${event.date}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}