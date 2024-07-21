package com.internship.expensetracker.util

import com.internship.expensetracker.R
import com.internship.expensetracker.data.models.Category

object Constant {
    const val TRANSACTION_TYPE = "transaction_type"
    const val EXPENSE = "expense"
    const val INCOME = "income"
    const val TRANSACTION_ID = "user_id"

    val categories = ArrayList<Category>()
    fun setCategory() {
        categories.add(
            Category(
                "Shopping",
                R.drawable.ic_yellow_trash,
                R.color.yellow_20,
                R.color.yellow_100
            )
        )
        categories.add(
            Category(
                "Subscription",
                R.drawable.ic_subscription,
                R.color.violate_20,
                R.color.violate
            )
        )

        categories.add(
            Category(
                "Food",
                R.drawable.ic_food,
                R.color.red_20,
                R.color.red
            )
        )

        categories.add(
            Category(
                "Salary",
                R.drawable.ic_salary,
                R.color.green_20,
                R.color.green_100
            )
        )

        categories.add(
            Category(
                "Transportation",
                R.drawable.ic_transportation,
                R.color.blue_20,
                R.color.blue_100
            )
        )
    }

    fun monthsName(): List<String> {
        return listOf("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December")
    }
}