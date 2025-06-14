package com.bor96dev.domain.repository

import com.bor96dev.domain.entity.Transaction

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getAllTransactions(): List<Transaction>
}