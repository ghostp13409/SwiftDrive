package com.example.swiftdrive.features.cars

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
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


    var cars = mutableStateOf<List<Car>>(emptyList())
        private set

    var selectedCar by mutableStateOf<Car?>(null)
        private set

    //Select all cars
    fun selectCar(car: Car){
        selectedCar = car
    }

    fun loadCars(){
        cars.value = dbHelper.getAllCars()
    }

    //Validate Data


    fun addCar(){
        dbHelper.insertCar(year, make, model, pricePerDay, isAvailable, engineType, condition, category)
        loadCars()
        cars.value = dbHelper.getAllCars()
    }

    fun deleteCar(id: Int){
        dbHelper.deleteCar(id)
        loadCars()
    }

    private fun resetInputFields(){
        year = ""
        make = ""
        model = ""
        pricePerDay = ""
        isAvailable = false
        engineType = ""
        condition = ""
        category = ""

    }

}

