package com.internship.expensetracker.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.internship.expensetracker.data.models.User
import com.internship.expensetracker.util.PrefKeys
import com.internship.expensetracker.util.SharedPrefsManager

class LoginViewmodel(application: Application): AndroidViewModel(application) {
    private val prefs by lazy { SharedPrefsManager.getInstance(application.applicationContext) }
    private val auth = Firebase.auth

    fun loginUser(email: String, password: String,
                  onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke("$email Successfully login")
                    prefs.putBooleans(PrefKeys.IS_LOGGED_IN, true)
                    prefs.saveUser(User(email = email))
                }
                else
                    onError.invoke("Something Gonna wrong Please try Again")
            }
            .addOnFailureListener {
                it.localizedMessage?.let { it1 -> onError.invoke(it1) }
            }
    }

    fun forgetPassword(email: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    onSuccess.invoke("Please Checkout your email! we send password on your Email")
                else
                    onError.invoke(it.exception?.localizedMessage?.toString() ?: "error")
            }
            .addOnFailureListener { it.localizedMessage?.let { it1 -> onError.invoke(it1) } }

    }
}