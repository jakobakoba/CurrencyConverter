package com.bor96dev.domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: Int = 0,
    val from: String,
    val to: String,
    val fromAmount: Double,
    val toAmount: Double,
    val dateTime: LocalDateTime
)
