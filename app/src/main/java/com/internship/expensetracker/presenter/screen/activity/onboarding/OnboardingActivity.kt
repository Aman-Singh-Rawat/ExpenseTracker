package com.internship.expensetracker.presenter.screen.activity.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ActivityOnboardingBinding
import com.internship.expensetracker.presenter.screen.activity.AuthContainer

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, AuthContainer::class.java))
        }
        viewPagerFunctionality()
    }

    private fun viewPagerFunctionality() {
        binding.apply {
            viewPager.adapter = OnboardingAdapter(onBoardingList())
            viewPager.clipToPadding = false
            viewPager.clipChildren = false
            viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            dotsIndicator.attachTo(viewPager)
        }
    }

    private fun onBoardingList(): MutableList<OnboardingItem> {
        return mutableListOf(
            OnboardingItem(R.drawable.ic_gain_total_control_of_your_money,
            "Gain total control of your money",
            "Become your own money manager and make every cent count"),

            OnboardingItem(R.drawable.ic_know_where_your_money_goes,
            "Know where your money goes",
            "Track your transaction easily, with categories and financial report"),

            OnboardingItem(R.drawable.ic_planning_ahead,
                "Planning ahead",
                "Setup your budget for each category so you in control")
        )
    }
}