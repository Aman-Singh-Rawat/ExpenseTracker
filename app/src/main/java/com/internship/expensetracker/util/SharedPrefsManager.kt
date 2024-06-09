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
                instance =SharedPrefsManager(context)

            return instance!!
        }
    }

    fun saveUser(user: User) {
        putString(Keys.USER, gson.toJson(user))
    }

    private fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}