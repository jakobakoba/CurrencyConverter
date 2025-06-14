package com.example.currencyconverter.di

import com.bor96dev.domain.repository.AccountRepository
import com.bor96dev.domain.repository.RatesRepository
import com.bor96dev.domain.usecases.GetCurrencyRatesUseCase
import com.bor96dev.domain.usecases.impl.GetCurrencyRatesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideCurrencyToFlag(): Map<String, String> {
        return mapOf(
            "EUR" to "eu",
            "AUD" to "au",
            "BGN" to "bg",
            "BRL" to "br",
            "CAD" to "ca",
            "CHF" to "ch",
            "CNY" to "cn",
            "CZK" to "cz",
            "DKK" to "dk",
            "GBP" to "gb",
            "HKD" to "hk",
            "HRK" to "hr",
            "HUF" to "hu",
            "IDR" to "id",
            "ILS" to "il",
            "INR" to "in",
            "ISK" to "is",
            "JPY" to "jp",
            "KRW" to "kr",
            "MXN" to "mx",
            "MYR" to "my",
            "NOK" to "no",
            "NZD" to "nz",
            "PHP" to "ph",
            "PLN" to "pl",
            "RON" to "ro",
            "RUB" to "ru",
            "SEK" to "se",
            "SGD" to "sg",
            "THB" to "th",
            "TRY" to "tr",
            "USD" to "us",
            "ZAR" to "za",
        )
    }

    @Provides
    @Singleton
    fun provideGetCurrencyRatesUseCase(
        ratesRepository: RatesRepository,
        accountRepository: AccountRepository,
        currencyToFlagIdentifierMap: Map<String, String>
    ): GetCurrencyRatesUseCase {
        return GetCurrencyRatesUseCaseImpl(
            ratesRepository,
            accountRepository,
            currencyToFlagIdentifierMap
        )
    }
}