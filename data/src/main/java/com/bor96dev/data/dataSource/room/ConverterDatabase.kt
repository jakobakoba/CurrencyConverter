package com.bor96dev.data.dataSource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bor96dev.data.dataSource.room.account.dao.AccountDao
import com.bor96dev.data.dataSource.room.account.dbo.AccountDbo
import com.bor96dev.data.dataSource.room.converter.Converters
import com.bor96dev.data.dataSource.room.transaction.dao.TransactionDao
import com.bor96dev.data.dataSource.room.transaction.dbo.TransactionDbo

@Database(entities = [AccountDbo::class, TransactionDbo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ConverterDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}