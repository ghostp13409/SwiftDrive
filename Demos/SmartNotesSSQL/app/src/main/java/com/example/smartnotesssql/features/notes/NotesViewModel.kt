package com.example.smartnotesssql.features.notes

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.smartnotesssql.data.Note
import com.example.smartnotesssql.data.NotesDatabaseHelper

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val dbHelper = NotesDatabaseHelper(application)
    var notes = mutableStateOf<List<Note>>(emptyList())
        private set

    var title = mutableStateOf("")
    var content = mutableStateOf("")

    var isDarkMode = mutableStateOf(false)

    fun toggleDarkMode(){
        isDarkMode.value = !isDarkMode.value
    }


    fun loadNotes() {
        notes.value = dbHelper.getAllNotes()
    }

    fun addNote() {
        dbHelper.insertNote(title = title.value, content = content.value)
        loadNotes()
        resetInputFields()
    }

    fun deleteNote(id: Int){
        dbHelper.deleteNote(id)
        loadNotes()
    }

    private fun resetInputFields() {
        title.value = ""
        content.value = ""
    }



}