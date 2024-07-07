package com.internship.expensetracker.presenter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.internship.expensetracker.data.models.Transaction
import com.internship.expensetracker.presenter.dao.TransactionDao

@Database(entities = [Transaction::class], version = 1)
abstract class TransactionDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabaseInstance(context: Context): TransactionDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TransactionDatabase::class.java, "transactionDB"
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}