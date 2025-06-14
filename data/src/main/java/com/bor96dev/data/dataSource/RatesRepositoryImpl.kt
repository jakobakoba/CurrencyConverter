package com.bor96dev.data.dataSource

import com.bor96dev.data.dataSource.room.RatesService
import com.bor96dev.data.mapper.toDomain
import com.bor96dev.domain.entity.Rate
import com.bor96dev.domain.repository.RatesRepository

class RatesRepositoryImpl (
    private val remoteRatesService: RatesService
): RatesRepository {
    override suspend fun getRates(
        baseCurrency: String,
        amount: Double
    ): List<Rate> {
        val rateDtos = remoteRatesService.getRates(baseCurrency, amount)
        return rateDtos.map{it.toDomain()}
    }
}