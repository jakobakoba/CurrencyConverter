package com.bor96dev.data.dataSource.room.account.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bor96dev.data.dataSource.room.account.dbo.AccountDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg accounts: AccountDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountDbo)

    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountDbo>

    @Query("SELECT * FROM accounts")
    fun getAllAsFlow(): Flow<List<AccountDbo>>

    @Query("UPDATE accounts SET amount = :amount WHERE currency_code == :code")
    suspend fun updateAmount(code: String, amount: Double)

    @Query("SELECT amount FROM accounts WHERE currency_code = :code")
    suspend fun getAmountByCode(code: String): Double?
}