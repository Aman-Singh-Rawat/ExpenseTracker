package com.internship.expensetracker.presenter.screen.fragment.budget

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.BudgetItem
import com.internship.expensetracker.databinding.ActivityHomeContainerBinding
import com.internship.expensetracker.databinding.FragmentBudgetBinding
import com.internship.expensetracker.presenter.adapters.BudgetAdapter
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class BudgetFragment : Fragment() {
    private val budgetAdapter = BudgetAdapter()
    private lateinit var binding: FragmentBudgetBinding
    private var activityBinding: ActivityHomeContainerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetBinding.inflate(inflater, container, false)

        activityBinding?.let {
            it.bottomAppBar.visibility = View.VISIBLE
            it.mainFab.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.window?.statusBarColor = resources.getColor(R.color.violate, null)
        super.onViewCreated(view, savedInstanceState)

        binding.tvMonth.setOnClickListener { findNavController().navigate(R.id.detailBudgetFragment) }
        binding.fabCreateBtn.setOnClickListener { findNavController().navigate(R.id.createBudgetFragment) }
        binding.rvBudget.adapter = budgetAdapter
        budgetAdapter.updateUi(budgetList())
    }

    private fun budgetList(): MutableList<BudgetItem> {
        return mutableListOf(
            BudgetItem("Shopping", "Remaining $0",
            1000, 1200),
            BudgetItem("Transportation", "Remaining $0",
                700, 350),
            BudgetItem("Shopping", "Remaining $0",
                1000, 1200),
            BudgetItem("Transportation", "Remaining $0",
                700, 350),
            BudgetItem("Shopping", "Remaining $0",
                1000, 1200),
            BudgetItem("Transportation", "Remaining $0",
                700, 350))
    }

    fun setActivityBinding(binding: ActivityHomeContainerBinding) {
        activityBinding = binding
    }

    override fun onPause() {
        super.onPause()

        activityBinding?.let {
            it.bottomAppBar.visibility = View.GONE
            it.mainFab.hide()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        activityBinding = null
    }
}