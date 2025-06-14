package com.bor96dev.presentation.currencies

import androidx.lifecycle.ViewModel
import com.bor96dev.domain.usecases.GetCurrencyRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(CurrenciesUiState())
}