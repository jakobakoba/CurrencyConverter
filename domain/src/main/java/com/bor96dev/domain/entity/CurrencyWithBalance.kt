package com.bor96dev.domain.entity

data class CurrencyWithBalance (
    val currency: String,
    val rate: Double,
    val balance: Double,
    val flag: Int
)