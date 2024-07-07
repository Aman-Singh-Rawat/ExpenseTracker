package com.internship.expensetracker.presenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.internship.expensetracker.data.models.UserAddExpense
import com.internship.expensetracker.presenter.repository.ExpenseRepository

class ExpenseAddViewModel(repository: ExpenseRepository, application: Application): AndroidViewModel(application) {
    private var _expenseLiveData = MutableLiveData<UserAddExpense>()
    val expenseLiveData: LiveData<UserAddExpense>
        get() = _expenseLiveData

    private var userAddExpense = UserAddExpense()
    fun addUserImage(expenseAttachment: String) {
        userAddExpense = userAddExpense.copy(expenseAttachment = expenseAttachment)
        _expenseLiveData.value = userAddExpense
    }

    fun addAllExpenseData(category: String, description: String, wallet: String) {
        userAddExpense = userAddExpense.copy(expenseCategory = category,
            expenseDescription = description, expenseWallet = wallet,
            expenseTime = System.currentTimeMillis().toString()
        )

        _expenseLiveData.value = userAddExpense
    }

    fun destroyExpense() {
        _expenseLiveData.value = UserAddExpense()
        userAddExpense = UserAddExpense()
    }
}