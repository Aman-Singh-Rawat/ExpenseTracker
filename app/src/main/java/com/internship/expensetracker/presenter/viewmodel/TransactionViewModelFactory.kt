package com.internship.expensetracker.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.internship.expensetracker.presenter.repository.ExpenseRepository

class TransactionViewModelFactory(private val repository: ExpenseRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionViewModel(repository) as T
    }
}