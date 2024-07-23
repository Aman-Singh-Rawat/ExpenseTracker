package com.internship.expensetracker.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.presenter.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class BudgetViewModel(private val repository: BudgetRepository): ViewModel() {
    private val _budget = MutableLiveData<Budget>()
    val budget: LiveData<Budget> = _budget
    val budgetLiveData: LiveData<List<Budget>> = repository.getAllBudget()

    fun insertBudget(budget: Budget, onSuccess: (String) -> Unit, onFailure: (Throwable) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertBudget(budget).fold(
                onSuccess = {onSuccess.invoke(it)},
                onFailure = {onFailure.invoke(it)}
            )
        }
    }

    fun deleteBudget(budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBudget(budget)
        }
    }

    fun deleteBudgetWithId(budgetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBudgetWithId(budgetId)
        }
    }

    fun getSelectedBudget(startDate: Date, endDate: Date): LiveData<List<Budget>> {
        return repository.getSelectedBudget(startDate, endDate)
    }

    fun getBudget(budgetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _budget.postValue(repository.getBudget(budgetId))
        }
    }
}