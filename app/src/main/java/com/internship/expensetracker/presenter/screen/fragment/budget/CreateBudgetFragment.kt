package com.internship.expensetracker.presenter.screen.fragment.budget

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.internship.expensetracker.R
import com.internship.expensetracker.core.BaseFragment
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.databinding.FragmentCreateBudgetBinding
import com.internship.expensetracker.presenter.adapters.CustomSpinnerAdapter
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.BudgetRepository
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModel
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModelProvider
import java.util.Date
import java.util.UUID

class CreateBudgetFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateBudgetBinding

    private val viewModel: BudgetViewModel by viewModels {
        BudgetViewModelProvider(
            BudgetRepository(
                TransactionDatabase.getDatabaseInstance(requireActivity().applicationContext)
                    .budgetDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.BudgetTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        binding = FragmentCreateBudgetBinding.inflate(localInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
    }

    private fun setUpUi() {

        binding.btnContinue.setOnClickListener {

            val budgetMoney = if (binding.etBudgetMoney.text.isNullOrBlank()) {
                0.0
            } else {
                binding.etBudgetMoney.text.toString().toDouble()
            }
            val budgetCategory = binding.spinnerCategory.selectedItem.toString()
            var flag = binding.createBudgetSwitch.isChecked
            val sliderPercent = binding.createBudgetSlider.value.toInt()

            if (isValuesAreEmpty(budgetMoney, budgetCategory)) {
                viewModel.insertBudget(Budget(
                    UUID.randomUUID().toString(),
                    budgetMoney, budgetCategory, Date(), flag, sliderPercent
                ), {
                        showToast(it)
                    activity?.supportFragmentManager?.popBackStack()
                    }) { it.localizedMessage?.let { it1 -> showToast(it1) } }
            }
        }

        binding.imgCreateBudgetBack.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }

        binding.createBudgetSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.createBudgetSlider.visibility = View.VISIBLE
            } else {
                binding.createBudgetSlider.visibility = View.GONE
            }
        }

        binding.createBudgetSlider.setLabelFormatter { value: Float ->
            val percentage = (value).toInt()
            "$percentage%"
        }


        val spinnerItems = listOf("Shopping", "Subscription", "Food", "Salary", "Transportation")
        val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)
        binding.spinnerCategory.adapter = adapter
    }

    private fun isValuesAreEmpty(budgetMoney: Double, budgetCategory: String): Boolean {
        return if (budgetMoney <= 0) {
            showToast("Please input money of budget")
            false
        } else if (budgetCategory.isBlank()) {
            showToast("Please Select Budget Category")
            false
        } else {
            true
        }

    }
}