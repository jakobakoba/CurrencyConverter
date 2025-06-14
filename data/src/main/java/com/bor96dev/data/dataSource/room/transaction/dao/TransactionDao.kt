package com.bor96dev.data.dataSource.room.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bor96dev.data.dataSource.room.transaction.dbo.TransactionDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertAll(vararg transactions: TransactionDbo)

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDbo>

    @Query("SELECT * FROM transactions ORDER BY dateTime DESC")
    fun getAllOrderedByDate(): Flow<List<TransactionDbo>>
}