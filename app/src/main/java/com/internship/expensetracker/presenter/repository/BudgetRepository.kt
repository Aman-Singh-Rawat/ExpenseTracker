package com.internship.expensetracker.presenter.repository

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.internship.expensetracker.data.models.Budget
import com.internship.expensetracker.presenter.dao.BudgetDao
import java.util.Date

class BudgetRepository(private val dao: BudgetDao) {

    suspend fun insertBudget(budget: Budget): Result<String> {
        return try {
            dao.insertBudget(budget)
            Result.success("${budget.budgetCategory} add successfully")
        } catch (e: SQLiteConstraintException) {
            Result.failure(Exception("${budget.budgetCategory} already exists"))
        }

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

    suspend fun getBudget(budgetId: String): Budget {
        return dao.getBudget(budgetId)
    }
}