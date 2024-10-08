package com.internship.expensetracker.presenter.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.internship.expensetracker.data.models.Budget
import java.util.Date

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)

    @Query("DELETE FROM budget WHERE budgetId = :budgetId")
    suspend fun deleteBudgetWithId(budgetId: String)

    @Query("SELECT * FROM budget")
    fun getAllBudget(): LiveData<List<Budget>>

    @Query("SELECT * FROM budget WHERE budgetId = :budgetId")
    suspend fun getBudget(budgetId: String): Budget

    @Query("SELECT * FROM budget WHERE budgetDate >= :startDate AND budgetDate <= :endDate")
    fun getSelectedBudget(startDate: Date, endDate: Date): LiveData<List<Budget>>
}