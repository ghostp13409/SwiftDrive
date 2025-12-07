package com.example.swiftdrive.components.cars

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swiftdrive.data.models.*

// Filter Chip Component for filtering cars
@Composable
fun FilterChips(
        selectedEngineTypes: Set<EngineType>,
        onEngineTypeToggle: (EngineType) -> Unit,
        selectedConditions: Set<Condition>,
        onConditionToggle: (Condition) -> Unit,
        selectedCategories: Set<Category>,
        onCategoryToggle: (Category) -> Unit,
        selectedTiers: Set<Tier>,
        onTierToggle: (Tier) -> Unit,
        selectedAvailabilities: Set<Boolean>,
        onAvailabilityToggle: (Boolean) -> Unit,
        onClearAll: () -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
            modifier =
                    Modifier.horizontalScroll(scrollState)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // All Chip
        FilterChip(
                selected =
                        selectedEngineTypes.isEmpty() &&
                                selectedConditions.isEmpty() &&
                                selectedCategories.isEmpty() &&
                                selectedTiers.isEmpty() &&
                                selectedAvailabilities.isEmpty(),
                onClick = onClearAll,
                label = { Text("All") }
        )

        // Engine Type Chips
        EngineType.values().forEach { engineType ->
            FilterChip(
                    selected = engineType in selectedEngineTypes,
                    onClick = { onEngineTypeToggle(engineType) },
                    label = { Text(engineType.name) },
                    leadingIcon = {
                        Icon(
                                imageVector =
                                        when (engineType) {
                                            EngineType.ELECTRIC -> Icons.Filled.FlashOn
                                            EngineType.PETROL -> Icons.Filled.LocalGasStation
                                            EngineType.DIESEL -> Icons.Filled.LocalGasStation
                                            EngineType.HYBRID -> Icons.Filled.ElectricCar
                                        },
                                contentDescription = null,
                                tint =
                                        when (engineType) {
                                            EngineType.ELECTRIC -> Color(0xFF3498DB)
                                            EngineType.PETROL -> Color(0xFFE74C3C)
                                            EngineType.DIESEL -> Color(0xFF8B4513)
                                            EngineType.HYBRID -> Color(0xFF2ECC71)
                                        },
                                modifier = Modifier.size(16.dp)
                        )
                    }
            )
        }

        // Condition Chips
        Condition.values().forEach { condition ->
            FilterChip(
                    selected = condition in selectedConditions,
                    onClick = { onConditionToggle(condition) },
                    label = { Text(condition.name) }
            )
        }

        // Category Chips
        Category.values().forEach { category ->
            FilterChip(
                    selected = category in selectedCategories,
                    onClick = { onCategoryToggle(category) },
                    label = { Text(category.name) }
            )
        }

        // Tier Chips
        Tier.values().forEach { tier ->
            FilterChip(
                    selected = tier in selectedTiers,
                    onClick = { onTierToggle(tier) },
                    label = { Text(tier.name) },
                    leadingIcon = {
                        Icon(
                                imageVector =
                                        when (tier) {
                                            Tier.Economy -> Icons.Filled.AttachMoney
                                            Tier.PREMIUM -> Icons.Filled.Star
                                            Tier.LUXURY -> Icons.Filled.WorkspacePremium
                                        },
                                contentDescription = null,
                                tint =
                                        when (tier) {
                                            Tier.Economy -> Color.Gray
                                            Tier.PREMIUM -> Color(0xFFFFD700)
                                            Tier.LUXURY -> Color(0xFFB9F2FF)
                                        },
                                modifier = Modifier.size(16.dp)
                        )
                    }
            )
        }

        // Availability Chips
        listOf(true, false).forEach { availability ->
            FilterChip(
                    selected = availability in selectedAvailabilities,
                    onClick = { onAvailabilityToggle(availability) },
                    label = { Text(if (availability) "Available" else "Unavailable") }
            )
        }
    }
}
