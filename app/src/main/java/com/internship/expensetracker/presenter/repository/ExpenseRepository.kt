package com.internship.expensetracker.presenter.repository

import androidx.lifecycle.LiveData
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.presenter.dao.TransactionDao
import java.util.Date

class ExpenseRepository(private val dao: TransactionDao) {

    fun getAllTransaction(): LiveData<List<Transaction>> {
        return dao.getAllTransaction()
    }

    fun getSelectedTransaction(transactionId: String): LiveData<Transaction> {
        return dao.getSelectedTransaction(transactionId)
    }

    suspend fun insertTransaction(transaction: Transaction) {
        dao.insertExpense(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction)
    }

    fun getSumOfAllTransaction() : LiveData<Double> {
        return dao.getSumOfAllTransaction()
    }

    fun getSumOfIncome(): LiveData<Double> {
        return dao.getSumOfIncome()
    }
    fun getSumOfExpense(): LiveData<Double> {
        return dao.getSumOfExpense()
    }
    fun getTodayTransaction(startDate: Date, endDate: Date): LiveData<List<Transaction>> {
        return dao.getTodayTransaction(startDate, endDate)
    }
}