package com.example.swiftdrive.features.cars

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.swiftdrive.components.CarCard


@Composable
fun CarScreen(
    viewModel: CarsViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(viewModel.cars.value) { car ->
                CarCard(
                    car = car,
                    onEventClick = {
                        viewModel.selectCar(car)
                        onEventClick()
                    }
                )
            }
        }
    }
}