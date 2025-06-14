package com.bor96dev.domain.repository

import com.bor96dev.domain.entity.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun getAllAccounts(): List<Account>
    suspend fun getAccountByCode(code: String): Account?
    suspend fun updateAccount(account: Account)
    suspend fun initializeWithRubles()
    fun getAccountsFlow(): Flow<List<Account>>
}