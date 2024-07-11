package com.internship.expensetracker.util

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding

class FabManager(private val context: Context, private val binding: ActivityHomeContainerBinding) {
    private var isExpanded = false
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }

    fun onMainFabClicked() {
        isExpanded = !isExpanded
        updateFabState()
    }

    fun onIncomeFabClicked() {
        // Handle income action
        collapseFab()
    }

    fun onExpenseFabClicked() {
        collapseFab()
    }

    fun onOverlayClicked() {
        if (isExpanded)
            collapseFab()
    }

    private fun updateFabState() {
        if (isExpanded) {
            expandFab()
        } else {
            collapseFab()
        }
    }

    private fun expandFab() {
        binding.fabIncome.show()
        binding.fabExpense.show()
        binding.fabExpense.isClickable = true
        binding.fabExpense.isFocusable = true
        binding.fabIncome.isClickable = true
        binding.fabIncome.isFocusable = true
        binding.fabIncome.startAnimation(fromBottom)
        binding.fabExpense.startAnimation(fromBottom)
        binding.mainFab.startAnimation(rotateOpen)
        binding.bottomAppBar.fabCradleMargin = 20f
        binding.bottomAppBar.fabCradleRoundedCornerRadius = 20f
        binding.overlay.visibility = View.VISIBLE
    }

    private fun collapseFab() {
        binding.overlay.visibility = View.GONE
        binding.fabIncome.hide()
        binding.fabExpense.hide()
        binding.fabExpense.isClickable = false
        binding.fabExpense.isFocusable = false
        binding.fabIncome.isClickable = false
        binding.fabIncome.isFocusable = false
        binding.bottomAppBar.fabCradleMargin = 0f

        binding.fabIncome.startAnimation(toBottom)
        binding.fabExpense.startAnimation(toBottom)
        binding.mainFab.startAnimation(rotateClose)
        isExpanded = false
    }
}