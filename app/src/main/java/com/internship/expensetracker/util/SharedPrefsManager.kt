package com.internship.expensetracker.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.internship.expensetracker.data.models.User

class SharedPrefsManager private constructor(private val context: Context) {
    private val gson: Gson = Gson()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREFS_NAME = "expense_tracker_app"
        private var instance: SharedPrefsManager? = null

        @Synchronized
        fun getInstance(context: Context): SharedPrefsManager {
            if (instance == null)
                instance = SharedPrefsManager(context)

            return instance!!
        }
    }

    fun saveUser(user: User) {
        putString(Keys.USER, gson.toJson(user))
    }

    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putBooleans(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}

object PrefKeys {
    const val IS_LOGGED_IN = "is_logged_in"
    const val USER = "logged_in_user"
}