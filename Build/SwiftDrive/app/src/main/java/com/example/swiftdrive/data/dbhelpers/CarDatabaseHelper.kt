package com.example.swiftdrive.data.dbhelpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.swiftdrive.R
import com.example.swiftdrive.data.models.Car

class CarDatabaseHelper(context : Context) :
    SQLiteOpenHelper(context, "cars.db", null, 3){

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE cars(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                year INTEGER,
                make TEXT,
                model TEXT,
                pricePerDay REAL,
                isAvailable INTEGER,
                engineType TEXT,
                condition TEXT,
                category TEXT,
                imageRes INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)

        seedInitialCars(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cars")
        onCreate(db)
    }

    fun insertCar(year: Int, make: String, model: String, pricePerDay: Double, isAvailable: Boolean, engineType: String, condition: String, category: String, imageRes: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("year", year)
            put("make", make)
            put("model", model)
            put("pricePerDay", pricePerDay)
            put("isAvailable", if (isAvailable) 1 else 0)
            put("engineType", engineType)
            put("condition", condition)
            put("category", category)
            put("imageRes", imageRes)
        }
        db.insert("cars", null, values)
        db.close()
    }

    fun getAllCars(): List<Car> {
        val cars = mutableListOf<Car>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM cars", null)
        while (cursor.moveToNext()) {
            cars.add(
                Car(
                    id = cursor.getInt(0),
                    year = cursor.getInt(1),
                    make = cursor.getString(2),
                    model = cursor.getString(3),
                    pricePerDay = cursor.getDouble(4),
                    isAvailable = cursor.getInt(5) == 1,
                    engineType = cursor.getString(6),
                    condition = cursor.getString(7),
                    category = cursor.getString(8),
                    imageRes = cursor.getInt(9)
                )
            )
        }
        cursor.close()
        db.close()
        return cars
    }

    fun deleteCar(id: Int){
        val db = writableDatabase
        db.delete("cars", "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun updateCarAvailability(id: Int, isAvailable: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("isAvailable", if (isAvailable) 1 else 0)
        }
        db.update("cars", values, "id=?", arrayOf(id.toString()))
        db.close()
    }
    fun updateCar(car: Car) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("year", car.year)
            put("make", car.make)
            put("model", car.model)
            put("pricePerDay", car.pricePerDay)
            put("isAvailable", if (car.isAvailable) 1 else 0)
            put("engineType", car.engineType)
            put("condition", car.condition)
            put("category", car.category)
            put("imageRes", car.imageRes)
        }
        db.update("cars", values, "id=?", arrayOf(car.id.toString()))
        db.close()
    }

    private fun seedInitialCars(db: SQLiteDatabase?) {
        if (db == null) return

        fun seed(
            year: Int,
            make: String,
            model: String,
            price: Double,
            available: Boolean,
            engine: String,
            condition: String,
            category: String,
            imageRes: Int
        ) {
            val values = ContentValues().apply {
                put("year", year)
                put("make", make)
                put("model", model)
                put("pricePerDay", price)
                put("isAvailable", if (available) 1 else 0)
                put("engineType", engine)
                put("condition", condition)
                put("category", category)
                put("imageRes", imageRes)
            }
            db.insert("cars", null, values)
        }



        // BMW 3 Series
        seed(2023, "BMW", "3 Series", 120.0, true, "Gasoline", "Excellent", "Sedan", R.drawable.bmw3_1)
        seed(2023, "BMW", "3 Series", 125.0, true, "Gasoline", "Excellent", "Sedan", R.drawable.bmw3_2)

        // Ford Mustang
        seed(2023, "Ford", "Mustang", 140.0, true, "V8", "Excellent", "Sports", R.drawable.mustang_1)
        seed(2023, "Ford", "Mustang", 145.0, false, "V8", "Good", "Sports", R.drawable.mustang_2)

        // Honda Civic
        seed(2023, "Honda", "Civic", 60.0, true, "Gasoline", "Good", "Sedan", R.drawable.civic_1)
        seed(2023, "Honda", "Civic", 58.0, true, "Gasoline", "Good", "Sedan", R.drawable.civic_2)

        // Toyota Camry
        seed(2023, "Toyota", "Camry", 65.0, true, "Hybrid", "Excellent", "Sedan", R.drawable.camry_1)
        seed(2023, "Toyota", "Camry", 68.0, true, "Hybrid", "Excellent", "Sedan", R.drawable.camry_2)

        // Tesla Model 3
        seed(2023, "Tesla", "Model 3", 150.0, true, "Electric", "Excellent", "EV", R.drawable.model3_1)
        seed(2023, "Tesla", "Model 3", 155.0, false, "Electric", "Excellent", "EV", R.drawable.model3_2)

        // Mercedes C-Class
        seed(2024, "Mercedes-Benz", "C-Class", 160.0, true, "Gasoline", "Excellent", "Luxury", R.drawable.cclass_1)
        seed(2024, "Mercedes-Benz", "C-Class", 165.0, true, "Gasoline", "Excellent", "Luxury", R.drawable.cclass_2)
    }

}
