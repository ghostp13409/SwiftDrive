package com.example.swiftdrive.data.dbhelpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.swiftdrive.data.models.Rental

class RentalDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "rentals.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE rentals(
                id INTEGER PRIMARY KEY,
                userId INTEGER,
                carId INTEGER,
                rentalStart TEXT,
                rentalEnd TEXT,
                totalCost REAL,
                status TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS rentals")
        onCreate(db)
    }

    fun insertRental(id: Int, userId: Int, carId: Int, rentalStart: String, rentalEnd: String, totalCost: Double, status: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", id)
            put("userId", userId)
            put("carId", carId)
            put("rentalStart", rentalStart)
            put("rentalEnd", rentalEnd)
            put("totalCost", totalCost)
            put("status", status)
        }
        db.insert("rentals", null, values)
        db.close()
    }

    fun getAllRentals(): List<Rental> {
        val rentals = mutableListOf<Rental>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM rentals", null)
        while (cursor.moveToNext()) {
            rentals.add(
                Rental(
                    id = cursor.getInt(0),
                    userId = cursor.getInt(1),
                    carId = cursor.getInt(2),
                    rentalStart = cursor.getString(3),
                    rentalEnd = cursor.getString(4),
                    totalCost = cursor.getDouble(5),
                    status = cursor.getString(6)
                )
            )
        }
        cursor.close()
        db.close()
        return rentals
    }

    fun deleteRental(id: String) {
        val db = writableDatabase
        db.delete("rentals", "id=?", arrayOf(id))
        db.close()
    }

    fun updateRentalStatus(id: String, status: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("status", status)
        }
        db.update("rentals", values, "id=?", arrayOf(id))
        db.close()
    }

    fun seedRentals() {
        if (getAllRentals().isEmpty()) {
            insertRental(1, 1, 1, "2023-01-01", "2023-01-05", 250.0, "Completed")
            insertRental(2, 2, 2, "2023-02-01", "2023-02-03", 90.0, "Active")
        }
    }
}