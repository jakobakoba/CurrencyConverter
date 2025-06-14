package com.bor96dev.presentation.currencies

import com.bor96dev.domain.entity.CurrencyWithBalance

data class CurrenciesUiState (
    val viewMode: ViewMode = ViewMode.LIST,
    val selectedCurrency: String = "RUB",
    val inputAmount: Double = 1.0,
    val currencies: List<CurrencyWithBalance> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)