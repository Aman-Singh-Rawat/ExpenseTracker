package com.internship.expensetracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction")
data class Transaction (
    @PrimaryKey
    val transactionId: String,
    val transactionMoney: Double = 0.0,
    val category: String = "",
    val description: String = "",
    val wallet: String = "",
    val attachmentImage: String = "",
    val transactionTime: Date = Date(),
    val transactionType: String = ""
)
