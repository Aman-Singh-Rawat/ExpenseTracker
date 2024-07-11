package com.internship.expensetracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val transactionMoney: String = "",
    val category: String = "",
    val description: String = "",
    val wallet: String = "",
    val attachmentImage: String = "",
    val transactionTime: String = "",
    val transactionType: String = ""
)
