package com.bor96dev.domain.repository

import com.bor96dev.domain.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getAllTransactions(): List<Transaction>
    fun getTransactionsFlow(): Flow<List<Transaction>>
}