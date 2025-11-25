package com.example.eventexplorer8959999.features.eventlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eventexplorer8959999.data.Event
import com.example.eventexplorer8959999.data.sampleEvents

class EventListViewModel : ViewModel() {
    val events = sampleEvents   // Sample Events

    // Selected Event to pass to event detail screen
    var selectedEvent by mutableStateOf<Event>(events[0])
        private set

    // Switch Event to show in detail screen
    fun onEventSelected(event: Event) {
        selectedEvent = event
        selectedEventCategoryIcon = categoryIcons[event.category.toString()] ?: ""
    }

    // Collect location dynamically based on all unique locations in events list
    val locations = listOf("All") +  events.map { it.location }.distinct()

    // Expanded/Closed location menu state
    var isLocationMenuExpanded by mutableStateOf(false)
        private set
    fun updateLocationMenu(expanded: Boolean) {
        isLocationMenuExpanded = expanded
    }

    // Selected Location to filter events list by location
    var selectedLocation by mutableStateOf("All")
        private set

    fun onLocationSelected(location: String) {
        selectedLocation = location
    }

    // Categories List
    val categories = listOf("All", "Music", "Sports", "Workshop", "Exhibition")

    // Category Icons
    val categoryIcons = mapOf(
        "Music" to "🎵",
        "Sports" to "🏈",
        "Workshop" to "🛠️",
        "Exhibition" to "🏬"
    )

    // Selected Categories to filter events list by category
    var selectedCategories by mutableStateOf(setOf<String>("All"))
        private set

    // Currently selected event's icon for event detail screen
    var selectedEventCategoryIcon by mutableStateOf("")
        private set

    // Filtered Events by selected Location and Categories
    val filteredEvents: List<Event>
        get(){
            // Location filtering
            var filtered = if(selectedLocation == "All") {
                events
            } else {
                events.filter { it.location == selectedLocation }
            }

            // Category filtering
            if (!selectedCategories.contains("All")) {
                filtered = filtered.filter { it.category.toString() in selectedCategories }
            }

            return filtered
        }

    // Toggle Categories for event list
    fun toggleCategory(category: String) {
        val newSelectedCategories = selectedCategories.toMutableSet()
        if (category == "All") {
            newSelectedCategories.clear()
            newSelectedCategories.add("All")
        } else {
            newSelectedCategories.remove("All")
            if (newSelectedCategories.contains(category)) {
                newSelectedCategories.remove(category)
            } else {
                newSelectedCategories.add(category)
            }
            if (newSelectedCategories.isEmpty() || newSelectedCategories.size == categories.size - 1) {
                newSelectedCategories.clear()
                newSelectedCategories.add("All")
            }

        }
        selectedCategories = newSelectedCategories
    }
}