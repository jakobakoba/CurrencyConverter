package com.bor96dev.domain.usecases

interface ExchangeCurrencyUseCase {
    suspend operator fun invoke(
        fromCurrency: String,
        toCurrency: String,
        fromAmount: Double,
        toAmount: Double
    ): Result<Unit>
}