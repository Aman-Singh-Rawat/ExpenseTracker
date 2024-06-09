package com.internship.expensetracker.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.internship.expensetracker.data.models.User
import com.internship.expensetracker.util.SharedPrefsManager

class SignUpViewmodel(application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val auth = Firebase.auth

    fun createUser(name: String, email: String, password: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let { user ->
                        prefs.saveUser(User(userName = name, email = user.email ?: ""))
                    }
                    onSuccess.invoke("$email Successfully Registered User")
                } else
                    onError.invoke(task.exception.toString())

            }
            .addOnFailureListener { it.localizedMessage?.let { it1 -> onError.invoke(it1.toString()) } }
    }
}