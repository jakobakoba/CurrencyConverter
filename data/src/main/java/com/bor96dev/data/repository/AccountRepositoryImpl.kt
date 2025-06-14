package com.bor96dev.data.repository

import com.bor96dev.data.dataSource.room.account.dao.AccountDao
import com.bor96dev.data.dataSource.room.account.dbo.AccountDbo
import com.bor96dev.data.mapper.toDbo
import com.bor96dev.data.mapper.toDomain
import com.bor96dev.domain.entity.Account
import com.bor96dev.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun getAllAccounts(): List<Account> {
        return accountDao.getAll().map{it.toDomain()}
    }

    override suspend fun getAccountByCode(code: String): Account? {
        return accountDao.getAmountByCode(code)?.let {amount ->
            Account(currencyCode = code, amount = amount)
        }
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.insertAll(account.toDbo())
    }

    override suspend fun initializeWithRubles() {
        val rubAccount = accountDao.getAmountByCode("RUB")
        if (rubAccount == null){
            accountDao.insertAll(AccountDbo(code = "RUB", amount = 75000.0))
        }
    }

    override fun getAccountsFlow(): Flow<List<Account>> {
        return accountDao.getAllAsFlow().map{listDbo ->
            listDbo.map{it.toDomain()}
        }
    }
}