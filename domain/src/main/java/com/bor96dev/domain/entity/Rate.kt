package com.bor96dev.domain.entity

data class Rate(
    val currency: String,
    val value: Double,
    val flag: Int? = null
)
