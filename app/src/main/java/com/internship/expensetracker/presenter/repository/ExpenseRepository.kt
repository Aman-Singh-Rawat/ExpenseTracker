package com.internship.expensetracker.presenter.repository

import androidx.lifecycle.LiveData
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.presenter.dao.TransactionDao

class ExpenseRepository(private val dao: TransactionDao) {

    fun getAllTransaction(): LiveData<List<Transaction>> {
        return dao.getAllTransaction()
    }

    suspend fun insertTransaction(transaction: Transaction) {
        dao.insertExpense(transaction)
    }
}