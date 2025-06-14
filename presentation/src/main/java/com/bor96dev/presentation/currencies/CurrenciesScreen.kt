package com.bor96dev.presentation.currencies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bor96dev.domain.entity.CurrencyWithBalance

@Composable
fun CurrenciesScreen(
    viewModel: CurrenciesViewModel = hiltViewModel(),
    onNavigateToExchange: (fromCurrency: String, toCurrency: String, amount: Double) -> Unit
) {
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val amountToExchange by viewModel.amountToExchange.collectAsState()
    val isInputMode by viewModel.isInputMode.collectAsState()
    val currencyRates by viewModel.currencyRatesWithBalance.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startFetchingRates()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Валюты") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (isLoading && currencyRates.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)){
                    currencyRates.find{it.currency == selectedCurrency}?.let {topCurrency ->
                        item {
                            CurrencyRow (
                                currencyInfo = topCurrency,
                                isSelected = true,
                                isInputMode = isInputMode,
                                amountToExchange = amountToExchange,
                                onCurrencyClick = {
                                    if (!isInputMode){
                                        viewModel.setInputMode(true)
                                    }
                                },
                                onAmountChange = {newAmount ->
                                    viewModel.setAmountToExchange(newAmount)
                                },
                                onClearAmount = {
                                    viewModel.clearAmountAndExitInputMode()
                                }
                            )
                        }
                    }
                    items(currencyRates.filter {it.currency != selectedCurrency}){currencyInfo ->
                        CurrencyRow(
                            currencyInfo = currencyInfo,
                            isSelected = false,
                            isInputMode = false,
                            amountToExchange = currencyInfo.rate,
                            onCurrencyClick = {
                                if(isInputMode){
                                    onNavigateToExchange(selectedCurrency, currencyInfo.currency, amountToExchange)
                                } else {
                                    viewModel.selectCurrency(currencyInfo.currency)
                                }
                            },
                            onAmountChange = { },
                            onClearAmount = { }
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun CurrencyRow (
    currencyInfo: CurrencyWithBalance,
    isSelected: Boolean,
    isInputMode: Boolean,
    amountToExchange: Double,
    onCurrencyClick: () -> Unit,
    onAmountChange: (Double) -> Unit,
    onClearAmount: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onCurrencyClick()}
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)){
            Text(text = currencyInfo.currency, style = MaterialTheme.typography.titleMedium)
        }
        Column(horizontalAlignment = Alignment.End){
            if (isSelected && isInputMode){
                Row (verticalAlignment = Alignment.CenterVertically){
                    OutlinedTextField(
                        value = amountToExchange.toString(),
                        onValueChange = {textValue ->
                            textValue.toDoubleOrNull()?.let {onAmountChange(it)}
                        },
                        singleLine = true,
                        modifier = Modifier.width(100.dp)
                    )
                    IconButton(onClick = onClearAmount) {
                        Icon (Icons.Filled.Close, contentDescription = "close")
                    }
                }
            } else {
                Text(
                    text = String.format("%.2f", if(isSelected) amountToExchange else currencyInfo.rate),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (currencyInfo.balance > 0){
                Text(
                    text = "Баланс: ${String.format("%.2f", currencyInfo.balance)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
    HorizontalDivider()
}