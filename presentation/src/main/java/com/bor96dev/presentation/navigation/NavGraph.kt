package com.bor96dev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bor96dev.presentation.CurrenciesViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bor96dev.presentation.screens.CurrenciesScreen
import com.bor96dev.presentation.screens.ExchangeScreen
import com.bor96dev.presentation.screens.TransactionScreen

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