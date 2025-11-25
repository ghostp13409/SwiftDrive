package com.example.smartnotesssql.features.notes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartnotesssql.ui.theme.SmartNotesSSQLTheme

@Composable
fun SmartNotesApp(modifier: Modifier = Modifier) {
    val viewModel: NotesViewModel = viewModel()
    SmartNotesSSQLTheme (darkTheme = viewModel.isDarkMode.value) {
        NotesScreen(modifier)
    }
}