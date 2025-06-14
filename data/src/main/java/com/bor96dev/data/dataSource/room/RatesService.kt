package com.bor96dev.data.dataSource.room

import com.bor96dev.data.dataSource.remote.dto.RateDto

interface RatesService {
    suspend fun getRates(
        baseCurrencyCode: String,
        amount: Double
    ): List<RateDto>
}