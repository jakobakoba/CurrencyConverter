package com.bor96dev.presentation.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bor96dev.domain.repository.AccountRepository
import com.bor96dev.domain.usecases.GetCurrencyRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase,
    private val accountRepository: AccountRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(CurrenciesUiState())
    val uiState: StateFlow<CurrenciesUiState> = _uiState.asStateFlow()

    private var ratesUpdateJob: Job? = null

    init {
        initializeAccounts()
        _uiState.update { it.copy(rawInputString = it.inputAmount.let { amount ->
            if (amount == amount.toLong().toDouble()) amount.toLong().toString() else String.format("%.2f", amount).replace(",",".")
        })}
        startPeriodicRatesUpdate()
    }

    private fun initializeAccounts() {
        viewModelScope.launch {
            accountRepository.initializeWithRubles()
        }
    }

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
        _uiState.update { currentState ->
            currentState.copy(
                selectedCurrency = currencyCode,
                viewMode = ViewMode.LIST,
                inputAmount = 1.0,
                rawInputString = "1"
            )
        }
    }

    fun setInputMode() {
        _uiState.update { currentState ->
            currentState.copy(
                viewMode = ViewMode.INPUT
            )
        }
    }

    fun setListMode() {
        _uiState.update { currentState ->
            currentState.copy(
                viewMode = ViewMode.LIST,
                inputAmount = 1.0,
                rawInputString = "1"
            )
        }
    }

    fun updateInput(rawString: String) {
        val newAmount = if (rawString.isEmpty() || rawString == ".") {
            0.0
        } else {
            rawString.toDoubleOrNull() ?: _uiState.value.inputAmount
        }

        _uiState.update { currentState ->
            currentState.copy(
                inputAmount = newAmount,
                rawInputString = rawString
            )
        }
    }


    private fun loadRates() {
        viewModelScope.launch {
            val currentSelectedCurrency = _uiState.value.selectedCurrency
            val currentInputAmount = _uiState.value.inputAmount
            val currentViewMode = _uiState.value.viewMode

            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val rates = getCurrencyRatesUseCase(
                    baseCurrency = currentSelectedCurrency,
                    amount = currentInputAmount,
                    filterByBalance = currentViewMode == ViewMode.INPUT
                )
                _uiState.update {
                    if (it.selectedCurrency == currentSelectedCurrency &&
                        it.inputAmount == currentInputAmount &&
                        it.viewMode == currentViewMode) {
                        it.copy(currencies = rates, isLoading = false)
                    } else {
                        it.copy(isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    if (it.selectedCurrency == currentSelectedCurrency &&
                        it.inputAmount == currentInputAmount &&
                        it.viewMode == currentViewMode) {
                        it.copy(isLoading = false, error = e.message ?: "Неизвестная ошибка")
                    } else {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopPeriodicRatesUpdate()
    }
}