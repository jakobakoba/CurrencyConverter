package com.bor96dev.domain.usecases

import com.bor96dev.domain.entity.CurrencyWithBalance

interface GetCurrencyRatesUseCase {
    suspend operator fun invoke(
        baseCurrency: String,
        amount: Double,
        filterByBalance: Boolean = false
    ): List<CurrencyWithBalance>
}