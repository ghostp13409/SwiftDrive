package com.example.eventeasy.data.repository

import com.example.eventeasy.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EventRepository {

    private val db = FirebaseFirestore.getInstance()

    private val eventsRef = db.collection("events")

    suspend fun addEvent(event: Event) {
        eventsRef.document(event.id).set(event).await()
    }

    suspend fun getEvents(): List<Event> {
        return eventsRef.get().await().documents.mapNotNull { it.toObject(Event::class.java) }
    }
}