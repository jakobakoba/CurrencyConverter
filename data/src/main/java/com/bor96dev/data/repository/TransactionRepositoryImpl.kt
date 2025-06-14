package com.bor96dev.data.repository

import com.bor96dev.data.dataSource.room.transaction.dao.TransactionDao
import com.bor96dev.data.mapper.toDbo
import com.bor96dev.data.mapper.toDomain
import com.bor96dev.domain.entity.Transaction
import com.bor96dev.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionRepository {
    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertAll(transaction.toDbo())
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAll().sortedByDescending { it.dateTime }.map{
            it.toDomain()
        }
    }

    override fun getTransactionsFlow(): Flow<List<Transaction>> {
        return transactionDao.getAllOrderedByDate().map { dboList ->
            dboList.map { dbo ->
                dbo.toDomain()
            }
        }
    }

}