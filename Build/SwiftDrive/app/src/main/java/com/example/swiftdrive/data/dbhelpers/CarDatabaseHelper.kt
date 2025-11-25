package com.example.swiftdrive.data.dbhelpers

import android.R
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.swiftdrive.data.models.Car

class CarDatabaseHelper(context : Context) :
    SQLiteOpenHelper(context, "cars.db", null, 1){

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
                category TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cars")
        onCreate(db)
    }

    fun insertCar(year: String, make: String, model: String, pricePerDay: String, isAvailable: Boolean, engineType: String, condition: String, category: String) {
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
                    category = cursor.getString(8)
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

}
