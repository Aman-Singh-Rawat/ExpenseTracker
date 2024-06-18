package com.internship.expensetracker.presenter.screen.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding
import com.internship.expensetracker.presenter.screen.fragment.home.ExpenseAddFragment

class HomeContainerActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }
    private lateinit var binding: ActivityHomeContainerBinding
    private var clicked = false
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
//        binding.mainFab.setOnClickListener {
//            onAddButtonClicked()
//        }
//
//        binding.fabIncome.setOnClickListener {
//
//        }

//        binding.fabExpense.setOnClickListener {
//            onAddButtonClicked()
//            binding.mainFab.visibility = View.GONE
//            val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
//            if (currentFragment is FabCallback) {
//                currentFragment.onFabClicked()
//            }
//        }
        binding.bottomNavView.menu.getItem(2).setEnabled(false)
    }

    private fun setupBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerHome) as NavHostFragment

        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavView, navController)
        navController.addOnDestinationChangedListener {_, destination, _ ->
            binding.bottomNavView.isVisible =
                destination.id == R.id.homeFragment || destination.id == R.id.transactionFragment
                        || destination.id == R.id.navigation_budget
                        || destination.id == R.id.navigation_profile

        }

    }

//    private fun onAddButtonClicked() {
//        setVisibility(clicked)
//        //setAnimation(clicked)
//        clicked = !clicked
//    }

//    private fun setAnimation(clicked: Boolean) {
//        if (!clicked) {
//            binding.fabIncome.startAnimation(fromBottom)
//            binding.fabExpense.startAnimation(fromBottom)
//            binding.mainFab.startAnimation(rotateOpen)
//        } else {
//            binding.fabIncome.startAnimation(toBottom)
//            binding.fabExpense.startAnimation(toBottom)
//            binding.mainFab.startAnimation(rotateClose)
//        }
//    }
//
//    private fun setVisibility(clicked: Boolean) {
//        if (!clicked) {
//            binding.fabIncome.visibility = View.VISIBLE
//            binding.fabExpense.visibility = View.VISIBLE
//        } else {
//            binding.fabIncome.visibility = View.GONE
//            binding.fabExpense.visibility = View.GONE
//        }
//    }

    interface FabCallback {
        fun onFabClicked()
    }
}