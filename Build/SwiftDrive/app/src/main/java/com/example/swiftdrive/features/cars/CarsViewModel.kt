package com.example.swiftdrive.features.cars

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftdrive.R
import com.example.swiftdrive.data.models.Car
import com.example.swiftdrive.data.models.Category
import com.example.swiftdrive.data.models.Condition
import com.example.swiftdrive.data.models.EngineType
import com.example.swiftdrive.data.models.Tier
import com.example.swiftdrive.data.repositories.CarRepository
import com.example.swiftdrive.features.signup.ValidationResult
import kotlinx.coroutines.launch

// Cars viw model for managing cars
class CarsViewModel(application: Application, val onChange: (() -> Unit)? = null) :
    AndroidViewModel(application) {
    private val carRepository = CarRepository(application)

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var year by mutableStateOf("")

    var make by mutableStateOf("")
    var model by mutableStateOf("")
    var pricePerDay by mutableStateOf("")
    var isAvailable by mutableStateOf(false)
    var engineType by mutableStateOf(EngineType.PETROL)
    var condition by mutableStateOf(Condition.NEW)
    var category by mutableStateOf(Category.SEDAN)
    var tier by mutableStateOf(Tier.Economy)
    var imageRes by mutableStateOf(R.drawable.logo)

    var cars = mutableStateOf<List<Car>>(emptyList())
        private set

    var selectedCar by mutableStateOf<Car?>(null)
        private set

    // Filter states
    var selectedEngineTypes by mutableStateOf(setOf<EngineType>())
    var selectedConditions by mutableStateOf(setOf<Condition>())
    var selectedCategories by mutableStateOf(setOf<Category>())
    var selectedTiers by mutableStateOf(setOf<Tier>())
    var selectedAvailabilities by mutableStateOf(setOf<Boolean>())

    //   List for filtered cars
    val filteredCars: List<Car>
        get() =
            cars.value.filter { car ->
                (selectedEngineTypes.isEmpty() || car.engineType in selectedEngineTypes) &&
                        (selectedConditions.isEmpty() || car.condition in selectedConditions) &&
                        (selectedCategories.isEmpty() || car.category in selectedCategories) &&
                        (selectedTiers.isEmpty() || car.tier in selectedTiers) &&
                        (selectedAvailabilities.isEmpty() ||
                                car.isAvailable in selectedAvailabilities)
            }

    // function for toggling filter states
    fun toggleEngineType(engineType: EngineType) {
        selectedEngineTypes =
            if (engineType in selectedEngineTypes) {
                selectedEngineTypes - engineType
            } else {
                selectedEngineTypes + engineType
            }
    }

    // Toggle condition for filtering cars
    fun toggleCondition(condition: Condition) {
        selectedConditions =
            if (condition in selectedConditions) {
                selectedConditions - condition
            } else {
                selectedConditions + condition
            }
    }

    // Toggle Category for filtering cars
    fun toggleCategory(category: Category) {
        selectedCategories =
            if (category in selectedCategories) {
                selectedCategories - category
            } else {
                selectedCategories + category
            }
    }

    // Toggle Tier for filtering cars
    fun toggleTier(tier: Tier) {
        selectedTiers =
            if (tier in selectedTiers) {
                selectedTiers - tier
            } else {
                selectedTiers + tier
            }
    }

    // Toggle Availability for filtering cars
    fun toggleAvailability(availability: Boolean) {
        selectedAvailabilities =
            if (availability in selectedAvailabilities) {
                selectedAvailabilities - availability
            } else {
                selectedAvailabilities + availability
            }
    }

    // Function for clear all filters
    fun clearAllFilters() {
        selectedEngineTypes = emptySet()
        selectedConditions = emptySet()
        selectedCategories = emptySet()
        selectedTiers = emptySet()
        selectedAvailabilities = emptySet()
    }

    init {
        loadCars()
    }

    // Override onChange to also refresh car data
    private val _onChange: (() -> Unit)? = onChange
    fun notifyChange() {
        _onChange?.invoke()
        loadCars()
    }

    // Select all cars
    fun selectCar(car: Car) {
        selectedCar = car

        year = car.year.toString()
        make = car.make
        model = car.model
        pricePerDay = car.pricePerDay.toString()
        isAvailable = car.isAvailable
        engineType = car.engineType
        condition = car.condition
        category = car.category
        tier = car.tier
        imageRes = car.imageRes
    }

    fun loadCars() {
        cars.value = carRepository.getCars()
    }

    // Fetch from Firestore and update local
    fun fetchFromFirestore() {
        viewModelScope.launch {
            carRepository.fetchAndStoreCars()
            loadCars()
        }
    }

    // Refresh car data when rentals change
    fun refreshCarData() {
        fetchFromFirestore()
    }

    // Sync to Firestore
    fun syncToFirestore() {
        viewModelScope.launch { carRepository.syncToFirestore() }
    }

    // Validate Data

    fun addCar() {
        val id = System.currentTimeMillis().toInt()

        val newCar =
            Car(
                id = id,
                year = year.toInt(),
                make = make,
                model = model,
                pricePerDay = pricePerDay.toDouble(),
                isAvailable = isAvailable,
                engineType = engineType,
                condition = condition,
                category = category,
                tier = tier,
                imageRes = imageRes
            )
        carRepository.addCar(newCar)
        loadCars()
        notifyChange()
    }

    fun deleteCar(id: Int) {
        val carToDelete = cars.value.find { it.id == id } ?: return
        carRepository.deleteCar(carToDelete)
        loadCars()
        notifyChange()
    }

    fun resetInputFields() {
        year = ""
        make = ""
        model = ""
        pricePerDay = ""
        isAvailable = false
        engineType = EngineType.PETROL
        condition = Condition.NEW
        category = Category.SEDAN
        tier = Tier.Economy
        imageRes = R.drawable.logo
        selectedCar = null
    }

    fun updateCar() {
        val car = selectedCar ?: return

        val updatedCar =
            car.copy(
                year = year.toIntOrNull() ?: car.year,
                make = make,
                model = model,
                pricePerDay = pricePerDay.toDoubleOrNull() ?: car.pricePerDay,
                isAvailable = isAvailable,
                engineType = engineType,
                condition = condition,
                category = category,
                tier = tier,
                imageRes = imageRes
            )

        carRepository.updateCar(updatedCar)
        loadCars()
        notifyChange()
    }


    // For input validation
    fun onAddCarClick(): Boolean {
        val validations = listOf(
            validateYear(year),
            validateMake(make),
            validateModel(model),
            validatePricePerDay(pricePerDay)
        )
        val firstError = validations.firstOrNull { it is CarsValidationResult.Error }

        return if(firstError is CarsValidationResult.Error){
            errorMessage = firstError.message
            false
        }
        else{
            errorMessage = null
            true
        }

    }

    // All validations
    private fun validateYear(year: String): CarsValidationResult {
        if (year.isBlank()) {
            return CarsValidationResult.Error("Year cannot be empty")
        }
        val yearValue = year.toIntOrNull()
        if (yearValue == null) {
            return CarsValidationResult.Error("Year must be a number")
        }
        if (yearValue < 1900 || yearValue > 2026) {
            return CarsValidationResult.Error("Year must be between 1900 and 2026")

        }
        return CarsValidationResult.Success
    }

    private fun validateMake(make: String) =
        if (make.isBlank()) CarsValidationResult.Error("Make cannot be empty")
        else CarsValidationResult.Success

    private fun validateModel(model: String) =
        if (model.isBlank()) CarsValidationResult.Error("Model cannot be empty")
        else CarsValidationResult.Success

    private fun validatePricePerDay(pricePerDay: String): CarsValidationResult {
        if (pricePerDay.isBlank()) {
            return CarsValidationResult.Error("Price per day cannot be empty")
        }
        val pricePerDayValue = pricePerDay.toDoubleOrNull()
        if (pricePerDayValue == null) {
            return CarsValidationResult.Error("Price per day must be a number")
        }
        if (pricePerDayValue < 0) {
            return CarsValidationResult.Error("Price per day cannot be negative")
        }
        return CarsValidationResult.Success
    }


}
