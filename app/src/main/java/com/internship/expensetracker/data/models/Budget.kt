package com.internship.expensetracker.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "budget",
    indices = [Index(value = ["budgetCategory"], unique = true)]
)
data class Budget(
    @PrimaryKey
    val budgetId: String = "",
    val budgetMoney: Double = 0.0,
    val budgetCategory: String = "",
    val budgetDate: Date = Date(),
    val isAlert: Boolean = false,
    val budgetAlert: Int = 0
)
