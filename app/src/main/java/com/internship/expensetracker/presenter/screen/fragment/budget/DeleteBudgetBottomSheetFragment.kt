package com.internship.expensetracker.presenter.screen.fragment.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.expensetracker.R
import com.internship.expensetracker.databinding.FragmentDeleteBudgetBottomSheetBinding
import com.internship.expensetracker.presenter.database.TransactionDatabase
import com.internship.expensetracker.presenter.repository.BudgetRepository
import com.internship.expensetracker.presenter.screen.activity.HomeContainerActivity
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModel
import com.internship.expensetracker.presenter.viewmodel.BudgetViewModelProvider
import com.internship.expensetracker.util.Constant

class DeleteBudgetBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding:  FragmentDeleteBudgetBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val budgetId by lazy { arguments?.getString(Constant.TRANSACTION_ID) ?: "" }
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

        _binding = FragmentDeleteBudgetBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println(budgetId)
        binding.btnYes.setOnClickListener {
            viewModel.deleteBudgetWithId(budgetId)
            dismiss()
            (activity as HomeContainerActivity).onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}