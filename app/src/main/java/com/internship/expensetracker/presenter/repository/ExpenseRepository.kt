package com.internship.expensetracker.presenter.repository

import androidx.lifecycle.LiveData
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.presenter.dao.TransactionDao

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
}