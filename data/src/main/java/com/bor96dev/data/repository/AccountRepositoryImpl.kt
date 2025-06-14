package com.bor96dev.data.repository

import com.bor96dev.data.dataSource.room.account.dao.AccountDao
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
        val amount = accountDao.getAmountByCode(code)
        return if (amount != null){
            Account(currencyCode = code, amount = amount)
        } else null
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAmount(account.currencyCode, account.amount)
    }

    override suspend fun initializeWithRubles() {
        val existingRubles = accountDao.getAmountByCode("RUB")
        if(existingRubles  == null){
            val rublesAccount = Account(currencyCode = "RUB", amount = 75000.0)
            accountDao.insertAll(rublesAccount.toDbo())
        }
    }

    override fun getAccountsFlow(): Flow<List<Account>> {
        return accountDao.getAllAsFlow().map{dboList ->
            dboList.map{it.toDomain()}
        }
    }
}