package com.example.expensetracker8959999.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context):
SQLiteOpenHelper(context, "expenses", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
            CREATE TABLE expenses(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                amount REAL,
                category TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS expenses")
        onCreate(db)
    }

    fun insertExpense(title: String, amount: Double, category: ExpenseCategory) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("amount", amount)
            put("category", category.toString())
        }
        db.insert("expenses", null, values)
        db.close()
    }

    fun getAllExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM expenses", null)
        while (cursor.moveToNext()) {
            expenses.add(
                Expense(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2).toDouble(),
                    ExpenseCategory.valueOf(cursor.getString(3))
                )
            )
        }
        cursor.close()
        db.close()
        return expenses
    }

    fun updateExpense(id: Int, title: String, amount: Double,  category: ExpenseCategory) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("amount", amount)
            put("category", category.toString())
        }
        db.update("expenses", values, "id=?", arrayOf(id.toString()))
        db.close()
    }
    fun seedData() {
        insertExpense("Groceries", 50.0, ExpenseCategory.Food)
        insertExpense("Bus Ticket", 2.5, ExpenseCategory.Travel)
        insertExpense("Movie Night", 15.0, ExpenseCategory.Bills)
    }

    fun deleteExpense(id: Int){
        val db = writableDatabase
        db.delete("expenses", "id=?", arrayOf(id.toString()))
        db.close()
    }
}