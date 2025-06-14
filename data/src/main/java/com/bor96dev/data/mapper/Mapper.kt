package com.bor96dev.data.mapper

import com.bor96dev.data.dataSource.remote.dto.RateDto
import com.bor96dev.data.dataSource.room.account.dbo.AccountDbo
import com.bor96dev.data.dataSource.room.transaction.dbo.TransactionDbo
import com.bor96dev.domain.entity.Account
import com.bor96dev.domain.entity.Rate
import com.bor96dev.domain.entity.Transaction

fun RateDto.toDomain() = Rate(currency, value)

fun AccountDbo.toDomain() = Account(code, amount)
fun Account.toDbo() = AccountDbo(currencyCode, amount)

fun TransactionDbo.toDomain() = Transaction(id, from, to, fromAmount, toAmount, dateTime)
fun Transaction.toDbo() = TransactionDbo(id, from, to, fromAmount, toAmount, dateTime)