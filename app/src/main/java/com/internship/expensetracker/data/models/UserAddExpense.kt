package com.internship.expensetracker.data.models

data class UserAddExpense (
    val expenseMoney: String = "",
    val expenseCategory: String = "",
    val expenseDescription: String = "",
    val expenseWallet: String = "",
    val expenseAttachment: String = "",
    val expenseTime: String = ""
)