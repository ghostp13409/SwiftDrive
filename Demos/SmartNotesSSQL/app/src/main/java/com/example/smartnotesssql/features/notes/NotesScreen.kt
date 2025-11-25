package com.example.smartnotesssql.features.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartnotesssql.R
import com.example.smartnotesssql.data.Note

@Composable
fun NotesScreen(modifier: Modifier = Modifier) {
    val viewModel: NotesViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        OutlinedTextField(
            value = viewModel.title.value,
            onValueChange = { viewModel.title.value = it },
            label = { Text(stringResource(R.string.note_title))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        OutlinedTextField(
            value = viewModel.content.value,
            onValueChange = { viewModel.content.value = it },
            label = { Text(stringResource(R.string.note_content))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {viewModel.addNote()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(stringResource(R.string.add_note))
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(stringResource(R.string.dark_mode))
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = viewModel.isDarkMode.value,
                onCheckedChange = { viewModel.toggleDarkMode() }
            )
        }

        NotesList(
            notes = viewModel.notes.value,
            onDelete = {viewModel.deleteNote(it)}
        )
    }
}

@Composable
private fun NotesList(notes: List<Note>, onDelete: (Int) -> Unit){
    val snakebarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    SnackbarHost(hostState = snakebarHostState)
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) { note ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(note.title, fontWeight = FontWeight.Bold)
                        Text(note.content)
                    }
                    IconButton(
                        onClick = { onDelete(note.id) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Note",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
//    AlertDialog(
//        onDismissRequest = {viewModel.showDialogue.value = false}
//    )
}