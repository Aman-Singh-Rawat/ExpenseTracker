package com.internship.expensetracker.presenter.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.internship.expensetracker.data.models.Transaction
import java.util.Date

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Insert
    suspend fun insertExpense(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("DELETE FROM `transaction` WHERE transactionId = :transactionId")
    suspend fun deleteTransactionUsingId(transactionId: String)

    @Query("SELECT SUM(transactionMoney) FROM `transaction`")
    fun getSumOfAllTransaction() : LiveData<Double>

    @Query("SELECT SUM(transactionMoney) FROM `transaction` where transactionMoney < 0")
    fun getSumOfExpense(): LiveData<Double>

    @Query("SELECT SUM(transactionMoney) FROM `transaction` where transactionMoney > 0")
    fun getSumOfIncome(): LiveData<Double>

    @Query("SELECT * FROM `transaction` WHERE transactionId = :transactionId")
    fun getSelectedTransaction(transactionId: String): LiveData<Transaction>

    @Query("SELECT * FROM `transaction` WHERE transactionTime >= :startDate AND transactionTime < :endDate")
    fun getTodayTransaction(startDate: Date, endDate: Date): LiveData<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE category = :category")
    fun getTransactionUsingQuery(category: String): LiveData<List<Transaction>>
}