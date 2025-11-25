package com.example.smartnotesssql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartnotesssql.features.notes.NotesScreen
import com.example.smartnotesssql.features.notes.SmartNotesApp
import com.example.smartnotesssql.ui.theme.SmartNotesSSQLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartNotesSSQLTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SmartNotesApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

