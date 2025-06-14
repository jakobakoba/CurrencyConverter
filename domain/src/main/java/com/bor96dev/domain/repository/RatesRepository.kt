package com.bor96dev.domain.repository

import com.bor96dev.domain.entity.Rate

interface RatesRepository {
    suspend fun getRates(baseCurrency: String, amount: Double): List<Rate>
}