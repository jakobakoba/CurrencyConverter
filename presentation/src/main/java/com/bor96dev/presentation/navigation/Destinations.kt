package com.bor96dev.presentation.navigation

sealed class Destinations(val route: String) {

    object Currencies: Destinations("currencies")
    object Exchange: Destinations("exchange")
    object Transactions: Destinations("transactions")

}