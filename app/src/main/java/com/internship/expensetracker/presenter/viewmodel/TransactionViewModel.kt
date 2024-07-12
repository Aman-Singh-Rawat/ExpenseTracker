package com.internship.expensetracker.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getTodayTransaction(startDate: Date, endDate: Date): LiveData<List<Transaction>> {
        return repository.getTodayTransaction(startDate, endDate)
    }
}

//
//private var userAddExpense = Transaction()
//fun addUserImage(expenseAttachment: String) {
//    userAddExpense = userAddExpense.copy(attachmentImage = expenseAttachment)
//    _expenseLiveData.value = userAddExpense
//}
//
//fun addAllExpenseData(category: String, description: String, wallet: String) {
//    userAddExpense = userAddExpense.copy(category = category,
//        description = description, wallet = wallet,
//        transactionTime = Date()
//    )
//

//    _expenseLiveData.value = userAddExpense
//}