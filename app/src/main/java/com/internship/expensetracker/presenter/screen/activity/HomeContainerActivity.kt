package com.internship.expensetracker.presenter.screen.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding
import com.internship.expensetracker.presenter.screen.fragment.budget.BudgetFragment
import com.internship.expensetracker.presenter.screen.fragment.home.ExpenseAddFragment
import com.internship.expensetracker.presenter.screen.fragment.home.HomeFragment
import com.internship.expensetracker.presenter.screen.fragment.transaction.TransactionFragment

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
        passBindingToFragment()

        binding.mainFab.setOnClickListener {
            onAddButtonClicked()
        }

        binding.fabIncome.setOnClickListener {
            showMainFab()


        }

        binding.fabExpense.setOnClickListener {
            hideMainFab()
            onAddButtonClicked()
            val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            if (currentFragment is FabCallback) {
                currentFragment.onFabClicked()
            }
        }
        binding.bottomNavView.menu.getItem(2).setEnabled(false)
    }

    private fun hideMainFab() {
        binding.mainFab.hide()
    }

    private fun showMainFab() {
        binding.mainFab.show()
    }

    private fun setupBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerHome) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun passBindingToFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerHome) as NavHostFragment

        // Handle already attached fragments (like the start destination)
        navHostFragment.childFragmentManager.fragments.forEach { fragment ->
            handleFragment(fragment)
        }

        // Listen for newly attached fragments
        navHostFragment.childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            handleFragment(fragment)
        }
    }

    private fun handleFragment(fragment: Fragment) {
        when (fragment) {
            is HomeFragment -> fragment.setActivityBinding(binding)
            is TransactionFragment -> fragment.setActivityBinding(binding)
            is BudgetFragment -> fragment.setActivityBinding(binding)
            // Add other fragments as needed
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabIncome.startAnimation(fromBottom)
            binding.fabExpense.startAnimation(fromBottom)
            binding.mainFab.startAnimation(rotateOpen)

        } else {
            binding.fabIncome.startAnimation(toBottom)
            binding.fabExpense.startAnimation(toBottom)
            binding.mainFab.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabIncome.visibility = View.VISIBLE
            binding.fabExpense.visibility = View.VISIBLE
        } else {
            binding.fabIncome.visibility = View.GONE
            binding.fabExpense.visibility = View.GONE
        }
    }



    interface FabCallback {
        fun onFabClicked()
    }

}