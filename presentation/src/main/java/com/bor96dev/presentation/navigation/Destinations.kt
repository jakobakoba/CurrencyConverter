package com.bor96dev.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(val routeDefinition: String) { // Переименовал в routeDefinition для ясности

    object Currencies : Destinations("currencies")

    object Exchange : Destinations(
        routeDefinition = "exchange_screen"
    ) {
        const val FROM_CURRENCY_ARG = "fromCurrency"
        const val TO_CURRENCY_ARG = "toCurrency"
        const val AMOUNT_ARG = "amount"

        val routeWithArgs = "$routeDefinition/{$FROM_CURRENCY_ARG}/{$TO_CURRENCY_ARG}/{$AMOUNT_ARG}"

        val arguments = listOf(
            navArgument(FROM_CURRENCY_ARG) { type = NavType.StringType },
            navArgument(TO_CURRENCY_ARG) { type = NavType.StringType },
            navArgument(AMOUNT_ARG) { type = NavType.StringType }
        )

        fun createRoute(fromCurrency: String, toCurrency: String, amount: Double): String {
            return "$routeDefinition/$fromCurrency/$toCurrency/$amount"
        }
    }

    object Transactions : Destinations("transactions")
}