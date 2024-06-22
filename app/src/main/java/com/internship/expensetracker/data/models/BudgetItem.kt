package com.internship.expensetracker.data.models

data class BudgetItem(
    val budgetType: String,
    val budgetRemaining: String,
    val budgetMax: Int,
    val budgetAchieve: Int
)