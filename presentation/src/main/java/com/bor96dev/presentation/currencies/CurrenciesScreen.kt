package com.bor96dev.presentation.currencies

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bor96dev.domain.entity.CurrencyWithBalance
import com.bor96dev.presentation.R
import androidx.compose.runtime.getValue
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(
    viewModel: CurrenciesViewModel = hiltViewModel(),
    onNavigateToExchange: (fromCurrency: String, toCurrency: String, amount: Double) -> Unit,
    onNavigateToTransactions: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startPeriodicRatesUpdate()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Валюты") },
                actions = {
                    IconButton(onClick = onNavigateToTransactions) {
                        Icon(
                            painter = painterResource(id = R.drawable.transactions_ic),
                            contentDescription = "История транзакций"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (uiState.isLoading && uiState.currencies.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    uiState.currencies.find { it.currency == uiState.selectedCurrency }?.let { topCurrency ->
                        item {
                            CurrencyRow(
                                currencyInfo = topCurrency,
                                isSelected = true,
                                isInputMode = uiState.viewMode == ViewMode.INPUT,
                                uiState = uiState,
                                onCurrencyClick = {
                                    if (uiState.viewMode == ViewMode.LIST) {
                                        viewModel.setInputMode()
                                    }
                                },
                                onAmountChange = { newRawString ->
                                    viewModel.updateInput(newRawString)
                                },
                                onClearAmount = {
                                    viewModel.setListMode()
                                }
                            )
                        }
                    }

                    items(uiState.currencies.filter { it.currency != uiState.selectedCurrency }) { currencyInfo ->
                        CurrencyRow(
                            currencyInfo = currencyInfo,
                            isSelected = false,
                            isInputMode = false,
                            uiState = uiState,
                            onCurrencyClick = {
                                if (uiState.viewMode == ViewMode.INPUT) {
                                    onNavigateToExchange(uiState.selectedCurrency, currencyInfo.currency, uiState.inputAmount)
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
fun CurrencyRow(
    currencyInfo: CurrencyWithBalance,
    isSelected: Boolean,
    isInputMode: Boolean,
    uiState: CurrenciesUiState,
    onCurrencyClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    onClearAmount: () -> Unit
) {
    val context = LocalContext.current
    val flagResId = remember(currencyInfo.flag) {
        val id = context.resources.getIdentifier(
            currencyInfo.flag,
            "drawable",
            context.packageName
        )
        if (id == 0) {
            context.resources.getIdentifier("ic_flag_placeholder", "drawable", context.packageName)
        } else {
            id
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCurrencyClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (flagResId != 0) {
            Image(
                painter = painterResource(id = flagResId),
                contentDescription = "${currencyInfo.currency} flag",
                modifier = Modifier.size(32.dp)
            )
        } else {
            Spacer(Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = currencyInfo.currency, style = MaterialTheme.typography.titleMedium)
            if (currencyInfo.balance > 0) {
                Text(
                    text = "Баланс: ${String.format("%.2f", currencyInfo.balance).replace(",",".")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (!isSelected) {
                Text(
                    text = "Нет на балансе",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            if (isSelected && isInputMode) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = uiState.rawInputString,
                        onValueChange = onAmountChange,
                        singleLine = true,
                        modifier = Modifier.widthIn(min = 80.dp, max = 120.dp),
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text("0.00", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())}
                    )
                    IconButton(onClick = onClearAmount) {
                        Icon(Icons.Filled.Close, contentDescription = "Очистить")
                    }
                }
            } else {
                val displayRate = if (isSelected) uiState.inputAmount else currencyInfo.rate
                Text(
                    text = String.format("%.2f", displayRate).replace(",","."),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End
                )
            }
        }
    }
    HorizontalDivider()
}