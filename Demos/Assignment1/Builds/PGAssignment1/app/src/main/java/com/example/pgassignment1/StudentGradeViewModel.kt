package com.example.pgassignment1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

// ViewModel for Student Grade Calculation Logic
class StudentGradeViewModel: ViewModel() {

    var studentName by mutableStateOf("")
        private  set
    var mathMarks by mutableStateOf("") // Changed to String
        private  set
    var scienceMarks by mutableStateOf("") // Changed to String
        private  set
    var englishMarks by mutableStateOf("") // Changed to String
        private set
    var averageMarks by mutableStateOf(0.0)
        private set
    var grade by mutableStateOf("A")
        private set

    var showResult by mutableStateOf(false)

    // Channel for Toast messages
    private val _toastEvents = Channel<String>()
    val toastEvents = _toastEvents.receiveAsFlow()

//    Calculate Grade and set showResult to true
    fun calculateGrade() {
        if (studentName.isBlank()) {
            sendToast("Please enter student name.")
            showResult = false
            return
        }

        val math = mathMarks.toIntOrNull()
        val science = scienceMarks.toIntOrNull()
        val english = englishMarks.toIntOrNull()

        if (math == null) {
            sendToast("Please enter valid Math marks.")
            showResult = false
            return
        }
        if (science == null) {
            sendToast("Please enter valid Science marks.")
            showResult = false
            return
        }
        if (english == null) {
            sendToast("Please enter valid English marks.")
            showResult = false
            return
        }

        if (math !in 0..100) {
            sendToast("Math marks must be between 0 and 100.")
            showResult = false
            return
        }
        if (science !in 0..100) {
            sendToast("Science marks must be between 0 and 100.")
            showResult = false
            return
        }
        if (english !in 0..100) {
            sendToast("English marks must be between 0 and 100.")
            showResult = false
            return
        }

        // All validations passed
        averageMarks = (math + science + english) / 3.0
        grade = when {
            averageMarks >= 90.0 -> "A"
            averageMarks in 80.0..89.9 -> "B"
            averageMarks in 70.0..79.9 -> "C"
            else -> "D"
        }
        showResult = true
    }

    private fun sendToast(message: String) {
        viewModelScope.launch {
            _toastEvents.send(message)
        }
    }

//    Reset ALl Values
    fun reset() {
        studentName = ""
        mathMarks = ""
        scienceMarks = ""
        englishMarks = ""
        averageMarks = 0.0
        grade = ""
        showResult = false
    }

    // Update Setter Methods
    fun updateStudentName(name: String) {
        studentName = name
        showResult = false // Hide result when data changes
    }
    fun updateMathMarks(marks: String) { // Parameter changed to String
        mathMarks = marks
        showResult = false // Hide result when data changes
    }
    fun updateScienceMarks(marks: String) { // Parameter changed to String
        scienceMarks = marks
        showResult = false // Hide result when data changes
    }
    fun updateEnglishMarks(marks: String) { // Parameter changed to String
        englishMarks = marks
        showResult = false // Hide result when data changes
    }
}