package com.example.eventexplorer8959999.features.eventlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventexplorer8959999.R
import com.example.eventexplorer8959999.components.EventCard
import com.example.eventexplorer8959999.data.Event
import com.example.eventexplorer8959999.ui.theme.EventExplorer8959999Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(viewModel: EventListViewModel, onEventClick: () -> Unit, modifier: Modifier = Modifier) {
    // Event List Screen
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn (
            modifier = Modifier.padding(8.dp)
        ) {
            // Page Title
            item {
                Text(
                    text = stringResource(R.string.event_list_title),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Location Filter Dropdown Menu
            item{
                Spacer(Modifier.width(8.dp))
                OptIn(ExperimentalMaterial3Api::class)
                ExposedDropdownMenuBox(
                    expanded = viewModel.isLocationMenuExpanded,
                    onExpandedChange = { viewModel.updateLocationMenu(!viewModel.isLocationMenuExpanded)}
                ) {
                    TextField(
                        value = "Location: ${viewModel.selectedLocation}",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = viewModel.isLocationMenuExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.isLocationMenuExpanded,
                        onDismissRequest = { viewModel.updateLocationMenu(false)}
                    ) {
                        viewModel.locations.forEach { location ->
                            DropdownMenuItem(
                                text = { Text( text = location) },
                                onClick = {
                                    viewModel.onLocationSelected(location)
                                    viewModel.updateLocationMenu(false)
                                }
                            )
                        }
                    }
                }
            }

            // Category Filter Chip Buttons
            item {
                Spacer(Modifier.width(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.categories) { category ->
                        val isSelected = viewModel.selectedCategories.contains(category)
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.toggleCategory(category) },
                            label = { Text(category) }
                        )
                    }
                }
            }

            // Event Cards
            items(viewModel.filteredEvents) { event ->
                EventCard(
                    event = event,
                    onEventClick = {
                        viewModel.onEventSelected(event)
                        onEventClick()
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EventListScreenPreview() {
    val viewModel : EventListViewModel = viewModel ()
    EventExplorer8959999Theme {
        EventListScreen(viewModel = viewModel, onEventClick = {})
    }
}