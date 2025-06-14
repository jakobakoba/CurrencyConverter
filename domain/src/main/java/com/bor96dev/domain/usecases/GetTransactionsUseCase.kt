package com.bor96dev.domain.usecases

import com.bor96dev.domain.entity.Transaction

interface GetTransactionsUseCase {
    suspend operator fun invoke(): List<Transaction>
}