package com.example.swiftdrive.data.dbhelpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.swiftdrive.data.models.Customer
import com.example.swiftdrive.data.models.UserRoles

class CustomerDatabaseHelper(context: Context) :
SQLiteOpenHelper(context, "customer", null, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE customer(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                firstName TEXT,
                lastName TEXT,
                email TEXT,
                phoneNumber TEXT
               
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS customer")
        onCreate(db)
    }

    // function for inserting the expense
    fun insertCustomer( roles: UserRoles,
                        firstName: String,
                        lastName: String,
                        age: Int,
                        phoneNumber: String,
                        drivingLicence: String?,
                        email: String,
                        password: String) {
        // defining the db into writable mode
        val db = writableDatabase
        val values = ContentValues().apply {

            put("roles", roles.toString())
            put("firstName", firstName)
            put("lastName", lastName)
            put("age", age)
            put("phoneNumber", phoneNumber)

            put("drivingLicence", drivingLicence)

            put("email", email)
            put("password", password)


        }
        db.insert("customer", null, values)
        db.close()
    }

    // function for getting all the customer
    fun getAllCustomers(): List<Customer> {
        val customers = mutableListOf<Customer>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM customer", null)
        while (cursor.moveToNext()) {
            customers.add(
                Customer(
                    cursor.getInt(0),
                    UserRoles.valueOf(cursor.getString(1)),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),

                    cursor.getString(6),

                    cursor.getString(7),
                    cursor.getString(8)


                )
            )
        }
        cursor.close()
        db.close()
        return customers
    }

    // Function for updating the Customer
    fun updateCustomer( id: Int,
                       roles: UserRoles,
                       firstName: String,
                       lastName: String,
                       age: Int,
                       phoneNumber: String,
                       drivingLicence: String?,
                       email: String,
                       password: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("roles", roles.toString())
            put("firstName", firstName)
            put("lastName", lastName)
            put("age", age)
            put("phoneNumber", phoneNumber)

            put("drivingLicence", drivingLicence)

            put("email", email)
            put("password", password)
        }
        db.update("customer", values, "id=?", arrayOf(id.toString()))
        db.close()
    }




    // function for deleting the customer
    fun deleteCustomer(id: Int){
        val db = writableDatabase
        db.delete("expenses", "id=?", arrayOf(id.toString()))
        db.close()
    }
}



