package com.internship.expensetracker.presenter.screen.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding
import com.internship.expensetracker.util.FabManager

class HomeContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeContainerBinding
    private lateinit var navController: NavController
    private val fabManager by lazy { FabManager(this, binding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupFab()
    }

    private fun setupFab() {
        binding.mainFab.setOnClickListener { fabManager.onMainFabClicked() }
        binding.fabIncome.setOnClickListener { fabManager.onIncomeFabClicked() }
        binding.fabExpense.setOnClickListener {
            fabManager.onExpenseFabClicked()
            navController.navigate(R.id.expenseAddFragment)
        }
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerHome) as NavHostFragment

        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.navigation_budget, R.id.transactionFragment -> {
                    fabManager.onOverlayClicked()
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.mainFab.show()
                }
                else -> {
                    binding.bottomAppBar.visibility = View.INVISIBLE
                    binding.mainFab.hide()
                }
            }
        }
        binding.bottomNavView.setupWithNavController(navController)
    }
}