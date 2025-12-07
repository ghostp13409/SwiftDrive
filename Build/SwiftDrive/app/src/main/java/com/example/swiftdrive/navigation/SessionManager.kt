package com.example.swiftdrive.navigation

import android.content.Context
import android.content.SharedPreferences

// Session Manager
class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
            context.getSharedPreferences("SwiftDrivePrefs", Context.MODE_PRIVATE)

    // Constants for SharedPreferences keys
    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_USER_ID = "user_id"
    }

    // Session management methods
    fun createLoginSession(id: Int, email: String, role: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putInt(KEY_USER_ID, id)
        editor.putString(KEY_USER_EMAIL, email)
        editor.putString(KEY_USER_ROLE, role)
        editor.apply()
    }

    // functions  to check if user is logged in

    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
    // Check if user is logged in
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    // Getters for user info

    // gets user id
    fun getUserId(): Int? {
        return prefs.getInt(KEY_USER_ID, -1)
    }
    // gets user email
    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }
    // gets user role
    fun getUserRole(): String? {
        return prefs.getString(KEY_USER_ROLE, null)
    }
    // checks if user is admin
    fun isAdmin(): Boolean {
        return getUserRole() == "ADMIN"
    }
}
