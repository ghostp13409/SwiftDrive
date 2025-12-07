package com.example.swiftdrive.features.cars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.Car
import com.example.swiftdrive.components.cars.AvailabilityAndPriceSection
import com.example.swiftdrive.components.cars.DetailRow


// Car Detail Screen for displaying and editing car details
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// function for displaying and editing car details
fun CarDetailScreen(
    viewModel: CarsViewModel,
    onBackClick: () -> Unit,
    onEditClick: (Car) -> Unit,
    onBookClicked: (Car) -> Unit
) {
    val car = viewModel.selectedCar ?: return

    // Scaffold for the screen
    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 6.dp,
                tonalElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "${car.make} ${car.model}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            // --- Card Container ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {

                DetailRow("Year", car.year.toString())
                DetailRow("Category", car.category.toString())
                DetailRow("Condition", car.condition.toString())
                DetailRow("Engine", car.engineType.toString())
                DetailRow("Tier", car.tier.toString())
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    //EDIT
                    IconButton(onClick = { onEditClick(car) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    //DELETE
                    IconButton(onClick = {
                        viewModel.deleteCar(car.id)
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                // ───────────────────────────────────────
                Divider(modifier = Modifier.padding(vertical = 18.dp))

                // ────────────────────ON BOOK BUTTON CLICKED───────────────────
                AvailabilityAndPriceSection(car, onBookClicked = onBookClicked)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Car Image
            Image(
                painter = painterResource(id = car.imageRes),
                contentDescription = "Car Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
