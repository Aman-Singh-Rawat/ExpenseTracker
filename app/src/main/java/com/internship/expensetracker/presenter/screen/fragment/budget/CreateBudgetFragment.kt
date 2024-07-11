package com.internship.expensetracker.presenter.screen.fragment.budget

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentCreateBudgetBinding
import com.internship.expensetracker.presenter.adapters.CustomSpinnerAdapter
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity

class CreateBudgetFragment : Fragment() {
    private lateinit var binding: FragmentCreateBudgetBinding
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

        binding.imgCreateBudgetBack.setOnClickListener {
            (activity as HomeContainerActivity).onBackPressed()
        }

        binding.createBudgetSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.createBudgetSlider.visibility = View.VISIBLE
                Log.d("debugging", "true")
            }
            else {
                Log.d("debugging", "false")
                binding.createBudgetSlider.visibility = View.GONE
            }
        }

        binding.createBudgetSlider.setLabelFormatter { value: Float ->
            val percentage = (value).toInt()
            "$percentage%"
        }


        val spinnerItems = listOf("Aman", "Rajveer", "Tanuj", "Vipul")
        val adapter = CustomSpinnerAdapter(requireContext(), spinnerItems)
        binding.spinnerCategory.adapter = adapter
    }
}