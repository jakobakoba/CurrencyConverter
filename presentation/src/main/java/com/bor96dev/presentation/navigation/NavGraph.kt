package com.bor96dev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bor96dev.presentation.currencies.CurrenciesScreen
import com.bor96dev.presentation.currencies.CurrenciesViewModel
import com.bor96dev.presentation.exchange.ExchangeScreen
// import com.bor96dev.presentation.exchange.ExchangeViewModel // Если ExchangeScreen использует свой ViewModel
import com.bor96dev.presentation.transaction.TransactionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    currenciesViewModel: CurrenciesViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Currencies.routeDefinition,
        modifier = modifier
    ) {
        composable(Destinations.Currencies.routeDefinition) {
            CurrenciesScreen(
                viewModel = currenciesViewModel,
                onNavigateToExchange = { fromCurrency, toCurrency, amount ->
                    navController.navigate(Destinations.Exchange.createRoute(fromCurrency, toCurrency, amount))
                },
                onNavigateToTransactions = {
                    navController.navigate(Destinations.Transactions.routeDefinition)
                }
            )
        }

        composable(
            route = Destinations.Exchange.routeWithArgs,
            arguments = Destinations.Exchange.arguments
        ) { navBackStackEntry ->

            val fromCurrency = navBackStackEntry.arguments?.getString(Destinations.Exchange.FROM_CURRENCY_ARG)
            val toCurrency = navBackStackEntry.arguments?.getString(Destinations.Exchange.TO_CURRENCY_ARG)
            val amountString = navBackStackEntry.arguments?.getString(Destinations.Exchange.AMOUNT_ARG)

            ExchangeScreen(
                navController = navController,
                fromCurrency = fromCurrency ?: "",
                toCurrency = toCurrency ?: "",
                amount = amountString?.toDoubleOrNull() ?: 0.0

            )
        }

        composable(Destinations.Transactions.routeDefinition) {
            TransactionScreen(
                navController = navController
            )
        }
    }
}