package com.bor96dev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bor96dev.presentation.currencies.CurrenciesViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bor96dev.presentation.currencies.CurrenciesScreen
import com.bor96dev.presentation.exchange.ExchangeScreen
import com.bor96dev.presentation.transaction.TransactionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: CurrenciesViewModel,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Destinations.Currencies.route,
        modifier = modifier
    ) {
        composable(Destinations.Currencies.route){
            CurrenciesScreen()
        }
        composable(Destinations.Exchange.route){
            ExchangeScreen()
        }
        composable(Destinations.Transactions.route){
            TransactionScreen()
        }
    }
}