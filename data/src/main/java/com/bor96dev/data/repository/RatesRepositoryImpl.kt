package com.bor96dev.data.repository

import com.bor96dev.data.dataSource.remote.RemoteRatesServiceImpl
import com.bor96dev.data.dataSource.remote.dto.RateDto
import com.bor96dev.data.mapper.toDomain
import com.bor96dev.domain.entity.Rate
import com.bor96dev.domain.repository.RatesRepository

class RatesRepositoryImpl(
    private val remoteRatesService: RemoteRatesServiceImpl
): RatesRepository {
    override suspend fun getRates(
        baseCurrency: String,
        amount: Double
    ): List<Rate> {
        val rateDtos: List<RateDto> = remoteRatesService.getRates(baseCurrency, amount)
        return rateDtos.map{it.toDomain()}
    }
}