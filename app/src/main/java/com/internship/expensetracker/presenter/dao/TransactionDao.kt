package com.internship.expensetracker.presenter.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.internship.expensetracker.data.models.Transaction

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Insert
    suspend fun insertExpense(transaction: Transaction)
}