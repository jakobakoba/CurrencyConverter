package com.bor96dev.data.mapper

import com.bor96dev.data.dataSource.remote.dto.RateDto
import com.bor96dev.data.dataSource.room.account.dbo.AccountDbo
import com.bor96dev.data.dataSource.room.transaction.dbo.TransactionDbo
import com.bor96dev.domain.model.Account
import com.bor96dev.domain.model.Rate
import com.bor96dev.domain.model.Transaction

fun RateDto.toDomain() = Rate(currency, value)

fun AccountDbo.toDomain() = Account(code, amount)
fun Account.toDbo() = AccountDbo(code, amount)

fun TransactionDbo.toDomain() = Transaction(id, from, to, fromAmount, toAmount, dateTime)
fun Transaction.toDbo() = TransactionDbo(id, from, to, fromAmount, toAmount, dateTime)