package com.bor96dev.domain.usecases.impl

import com.bor96dev.domain.entity.CurrencyWithBalance
import com.bor96dev.domain.repository.AccountRepository
import com.bor96dev.domain.repository.RatesRepository
import com.bor96dev.domain.usecases.GetCurrencyRatesUseCase

class GetCurrencyRatesUseCaseImpl(
    private val ratesRepository: RatesRepository,
    private val accountRepository: AccountRepository,
    private val currencyToFlagIdentifierMap: Map<String, String>
) : GetCurrencyRatesUseCase {
    override suspend fun invoke(
        baseCurrency: String,
        amount: Double,
        filterByBalance: Boolean
    ): List<CurrencyWithBalance> {
        val ratesFromRepo = ratesRepository.getRates(baseCurrency, amount)
        val accounts = accountRepository.getAllAccounts().associateBy { it.currencyCode }
        val resultWithBalance = mutableListOf<CurrencyWithBalance>()

        for (rate in ratesFromRepo) {

            val accountBalance = accounts[rate.currencyCode]?.amount ?: 0.0
            val flagIdString = currencyToFlagIdentifierMap[rate.currencyCode] ?: "flag"

            val targetCurrencyAmountNeeded = rate.value

            val displayRate = when {
                rate.currencyCode == baseCurrency -> 1.0
                amount != 0.0 -> targetCurrencyAmountNeeded / amount
                else -> 0.0
            }

            if (filterByBalance) {
                if (rate.currencyCode == baseCurrency) {
                    resultWithBalance.add(
                        CurrencyWithBalance(
                            currency = rate.currencyCode,
                            rate = 1.0,
                            balance = accountBalance,
                            flag = flagIdString
                        )
                    )
                } else {
                    if (accountBalance >= targetCurrencyAmountNeeded) {
                        resultWithBalance.add(
                            CurrencyWithBalance(
                                currency = rate.currencyCode,
                                rate = displayRate,
                                balance = accountBalance,
                                flag = flagIdString
                            )
                        )
                    }
                }
            } else {
                resultWithBalance.add(
                    CurrencyWithBalance(
                        currency = rate.currencyCode,
                        rate = displayRate,
                        balance = accountBalance,
                        flag = flagIdString
                    )
                )
            }
        }
        return resultWithBalance.sortedByDescending { it.currency == baseCurrency }
    }
}