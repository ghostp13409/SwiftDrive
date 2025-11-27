package com.example.swiftdrive.features.cars

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.swiftdrive.R
import com.example.swiftdrive.data.dbhelpers.CarDatabaseHelper
import com.example.swiftdrive.data.models.Car

class CarsViewModel(application: Application) : AndroidViewModel(application) {


    private val dbHelper = CarDatabaseHelper(application)

    var year by mutableStateOf("")

    var make by mutableStateOf("")
    var model by mutableStateOf("")
    var pricePerDay by mutableStateOf("")
    var isAvailable by mutableStateOf(false)
    var engineType by mutableStateOf("")
    var condition by mutableStateOf("")
    var category by mutableStateOf("")
    var imageRes by mutableStateOf(R.drawable.logo)



    var cars = mutableStateOf<List<Car>>(emptyList())
        private set

    var selectedCar by mutableStateOf<Car?>(null)
        private set

    init {
        loadCars()
    }

    //Select all cars
    fun selectCar(car: Car){
        selectedCar = car

        year = car.year.toString()
        make = car.make
        model = car.model
        pricePerDay = car.pricePerDay.toString()
        isAvailable = car.isAvailable
        engineType = car.engineType
        condition = car.condition
        category = car.category
        imageRes = car.imageRes
    }

    fun loadCars(){
        cars.value = dbHelper.getAllCars()
    }


    //Validate Data


    fun addCar(){
        dbHelper.insertCar(
            year.toInt(), make, model, pricePerDay.toDouble(), isAvailable, engineType, condition, category,
            imageRes
        )
        loadCars()
        cars.value = dbHelper.getAllCars()
    }

    fun deleteCar(id: Int){
        dbHelper.deleteCar(id)
        loadCars()
    }

    fun resetInputFields() {
        year = ""
        make = ""
        model = ""
        pricePerDay = ""
        isAvailable = false
        engineType = ""
        condition = ""
        category = ""
        imageRes = R.drawable.logo
        selectedCar = null
    }


    fun updateCar() {
        val car = selectedCar ?: return

        val updatedCar = car.copy(
            year = year.toIntOrNull() ?: car.year,
            make = make,
            model = model,
            pricePerDay = pricePerDay.toDoubleOrNull() ?: car.pricePerDay,
            isAvailable = isAvailable,
            engineType = engineType,
            condition = condition,
            category = category,
            imageRes = imageRes
        )

        dbHelper.updateCar(updatedCar)
        loadCars()
    }


}
