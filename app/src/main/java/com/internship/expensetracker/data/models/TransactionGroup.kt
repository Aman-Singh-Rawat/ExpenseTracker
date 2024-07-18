package com.internship.expensetracker.data.models

import java.util.Date

data class TransactionGroup (
    val date: Date,
    val transactions: List<Transaction>
)