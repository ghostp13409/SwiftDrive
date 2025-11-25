package com.example.eventeasy.features.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventeasy.data.model.Event
import com.example.eventeasy.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel: ViewModel() {

    private val repository = EventRepository()
    val name = MutableStateFlow("")
    val description = MutableStateFlow("")
    val date = MutableStateFlow("")

    private val _events = MutableStateFlow<List<Event>>(emptyList())

    val events: StateFlow<List<Event>> = _events

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            _events.value = repository.getEvents()
        }
    }

    fun addEvent() {
        if(name.value.isNotBlank() && description.value.isNotBlank() && date.value.isNotBlank()) {
            val event = Event(
                id = System.currentTimeMillis().toString(),
                name = name.value,
                description = description.value,
                date = date.value
            )
            viewModelScope.launch {
                repository.addEvent(event)
                // Clear input fields after adding the event
                name.value = ""
                description.value = ""
                date.value = ""
                loadEvents()
            }
        }
    }
}