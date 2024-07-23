package com.internship.expensetracker.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.presenter.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class TransactionViewModel(private val repository: ExpenseRepository): ViewModel() {
    val transactionList: LiveData<List<Transaction>> = repository.getAllTransaction()
    val sumOfTransaction: LiveData<Double> = repository.getSumOfAllTransaction()
    val sumOfIncome: LiveData<Double> = repository.getSumOfIncome()
    val sumOfExpense: LiveData<Double> = repository.getSumOfExpense()


    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTransaction(transaction)
        }
    }

    fun getSelectedTransaction(transactionId: String): LiveData<Transaction> {
        return repository.getSelectedTransaction(transactionId)
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transaction)
        }
    }

    fun deleteTransactionUsingId(transactionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransactionUsingId(transactionId)
        }
    }

    fun getTodayTransaction(startDate: Date, endDate: Date): LiveData<List<Transaction>> {
        return repository.getTodayTransaction(startDate, endDate)
    }

    fun getTransactionUsingQuery(category: String): LiveData<List<Transaction>> {
        return repository.getTransactionUsingQuery(category)
    }
}