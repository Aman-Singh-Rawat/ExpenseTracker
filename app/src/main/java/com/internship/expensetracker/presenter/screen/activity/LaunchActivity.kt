package com.internship.expensetracker.presenter.screen.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.internship.expensetracker.R
import com.internship.expensetracker.presenter.screen.activity.onboarding.OnboardingActivity

class LaunchActivity : AppCompatActivity() {
    private val tvAppName: TextView
        get() = findViewById(R.id.tvMontra)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val alphaAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        tvAppName.startAnimation(alphaAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@LaunchActivity, OnboardingActivity::class.java))
            finish()
        }, 5000)

    }
}