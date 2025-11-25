package com.example.pgassignment1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign // Keep for potential direct use, though Column alignment is preferred
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pgassignment1.ui.theme.PGAssignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PGAssignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentGradeCalculator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Student Grade Calculator Layout
@Composable
fun StudentGradeCalculator(modifier: Modifier = Modifier) {
    val studentGradeViewModel: StudentGradeViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = studentGradeViewModel) {
        studentGradeViewModel.toastEvents.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Overall padding for the screen content
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // App Logo and Heading
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp)) // Crop the image to the rounded shape
                .border(
                    shape = RoundedCornerShape(10.dp),
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(Modifier.height(24.dp))

//        Input Fields
        OutlinedTextField(
            value = studentGradeViewModel.studentName,
            onValueChange = { studentGradeViewModel.updateStudentName(it) },
            label = { Text(stringResource(R.string.student_name_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = studentGradeViewModel.mathMarks,
            onValueChange = { newValue -> studentGradeViewModel.updateMathMarks(newValue) },
            label = { Text(stringResource(R.string.math_mark_label)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = studentGradeViewModel.scienceMarks,
            onValueChange = { newValue -> studentGradeViewModel.updateScienceMarks(newValue) },
            label = { Text(stringResource(R.string.science_mark_label)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = studentGradeViewModel.englishMarks,
            onValueChange = { newValue -> studentGradeViewModel.updateEnglishMarks(newValue) },
            label = { Text(stringResource(R.string.english_mark_label)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

//        Submit and Reset Buttons
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { studentGradeViewModel.calculateGrade() },
            ) {
                Text(stringResource(R.string.calculate_button_label))
            }
            Button(
                onClick = { studentGradeViewModel.reset() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(stringResource(R.string.reset_button_text))
            }
        }

        Spacer(Modifier.padding(24.dp))

//        Result Section if available
        if(studentGradeViewModel.showResult) {
            Result(
                studentGradeViewModel = studentGradeViewModel,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentGradeCalculatorPreview() {
    PGAssignment1Theme {
        StudentGradeCalculator()
    }
}

// Result Section
@Composable
fun Result(
    studentGradeViewModel: StudentGradeViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
//            Result Heading
            Text(
                text = stringResource(R.string.result_heading),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Student Name
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.student_name_result),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = studentGradeViewModel.studentName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            // Average Marks
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.average_marks_result),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "%.2f".format(studentGradeViewModel.averageMarks),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            // Grade
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.grade_result),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = studentGradeViewModel.grade,
                    style = MaterialTheme.typography.headlineSmall,
                    color = when (studentGradeViewModel.grade) {
                        "A" -> MaterialTheme.colorScheme.primary
                        "B" -> MaterialTheme.colorScheme.secondary
                        "C" -> MaterialTheme.colorScheme.tertiary
                        "D" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    PGAssignment1Theme {
        Result(studentGradeViewModel = viewModel())
    }
}