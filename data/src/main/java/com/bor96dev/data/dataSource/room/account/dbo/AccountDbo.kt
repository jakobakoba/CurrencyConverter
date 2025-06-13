package com.bor96dev.data.dataSource.room.account.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountDbo(
    @PrimaryKey
    @ColumnInfo(name = "currency_code")
    val code: String,
    val amount: Double
)