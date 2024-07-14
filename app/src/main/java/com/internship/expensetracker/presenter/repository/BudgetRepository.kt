package com.internship.expensetracker.presenter.repository

import androidx.lifecycle.LiveData
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.presenter.dao.BudgetDao
import java.util.Date

class BudgetRepository(private val dao: BudgetDao) {

    suspend fun insertBudget(budget: Budget) {
        dao.insertBudget(budget)
    }

    suspend fun deleteBudget(budget: Budget) {
        dao.deleteBudget(budget)
    }

    fun getAllBudget(): LiveData<List<Budget>> {
        return dao.getAllBudget()
    }

    fun getSelectedBudget(startDate: Date, endDate: Date): LiveData<List<Budget>> {
        return dao.getSelectedBudget(startDate, endDate)
    }
}