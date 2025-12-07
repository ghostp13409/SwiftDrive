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
import com.example.swiftdrive.components.cars.CarCard
import com.example.swiftdrive.components.cars.FilterChips

// Car Screen for displaying and filtering cars
@Composable
// Car screen function for displaying and filtering cars
fun CarScreen(modifier: Modifier = Modifier, onEventClick: () -> Unit, viewModel: CarsViewModel) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        // Filter chips for filtering cars
        FilterChips(
                selectedEngineTypes = viewModel.selectedEngineTypes,
                onEngineTypeToggle = viewModel::toggleEngineType,
                selectedConditions = viewModel.selectedConditions,
                onConditionToggle = viewModel::toggleCondition,
                selectedCategories = viewModel.selectedCategories,
                onCategoryToggle = viewModel::toggleCategory,
                selectedTiers = viewModel.selectedTiers,
                onTierToggle = viewModel::toggleTier,
                selectedAvailabilities = viewModel.selectedAvailabilities,
                onAvailabilityToggle = viewModel::toggleAvailability,
                onClearAll = viewModel::clearAllFilters
        )
        // Lazy column for displaying filtered cars
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.filteredCars) { car ->
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
