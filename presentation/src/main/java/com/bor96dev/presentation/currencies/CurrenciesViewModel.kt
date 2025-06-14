package com.bor96dev.presentation.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bor96dev.domain.usecases.GetCurrencyRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(CurrenciesUiState())
    val uiState: StateFlow<CurrenciesUiState> = _uiState.asStateFlow()

    private var ratesUpdateJob: Job? = null

    fun startPeriodicRatesUpdate(){
        ratesUpdateJob?.cancel()
        ratesUpdateJob = viewModelScope.launch {
            while(true){
                loadRates()
                delay(1000)
            }
        }
    }

    fun stopPeriodicRatesUpdate(){
        ratesUpdateJob?.cancel()
    }

    fun selectCurrency(currencyCode: String){
        _uiState.value =  _uiState.value.copy(
            selectedCurrency = currencyCode,
            viewMode = ViewMode.LIST,
            inputAmount = 1.0
        )
    }

    fun setInputMode(amount: Double){
        _uiState.value = _uiState.value.copy(
            viewMode = ViewMode.INPUT,
            inputAmount = amount
        )
    }

    fun setListMode(){
        _uiState.value = uiState.value.copy(
            viewMode = ViewMode.LIST,
            inputAmount = 1.0
        )
    }

    fun updateInputAmount(amount: Double){
        _uiState.value = _uiState.value.copy(inputAmount = amount)
        loadRates()
    }

    private fun loadRates() {
        viewModelScope.launch{
            val rates = getCurrencyRatesUseCase(
                baseCurrency = _uiState.value.selectedCurrency,
                amount = _uiState.value.inputAmount
            )
            _uiState.value =  _uiState.value.copy(currencies = rates)
        }
    }
}