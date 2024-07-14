package com.internship.expensetracker.presenter.screen.activity

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding
import com.internship.expensetracker.util.Constant
import com.internship.expensetracker.util.FabManager

class HomeContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeContainerBinding
    private lateinit var navController: NavController
    private val fabManager by lazy { FabManager(this, binding) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        setupBottomNavigation()
        setupFab()
    }

    private fun setupFab() {
        binding.mainFab.setOnClickListener { fabManager.onMainFabClicked() }
        binding.fabIncome.setOnClickListener {
            fabManager.onIncomeFabClicked()
            navController.navigate(R.id.expenseAddFragment,
                bundleOf(Constant.TRANSACTION_TYPE to Constant.INCOME))
        }
        binding.fabExpense.setOnClickListener {
            fabManager.onExpenseFabClicked()
            navController.navigate(R.id.expenseAddFragment,
                bundleOf(Constant.TRANSACTION_TYPE to Constant.EXPENSE))
        }
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerHome) as NavHostFragment

        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.navigation_budget,
                R.id.transactionFragment, R.id.profileFragment -> {
                    fabManager.onOverlayClicked()
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.mainFab.isClickable = true
                    binding.mainFab.show()
                }
                else -> {
                    binding.bottomAppBar.visibility = View.INVISIBLE
                    binding.mainFab.isClickable = false
                    binding.mainFab.hide()
                }
            }
        }
        binding.bottomNavView.setupWithNavController(navController)
    }
}