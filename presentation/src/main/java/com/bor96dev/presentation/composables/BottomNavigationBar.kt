package com.bor96dev.presentation.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bor96dev.presentation.currencies.CurrenciesViewModel
import com.bor96dev.presentation.R
import com.bor96dev.presentation.navigation.Destinations
import androidx.compose.runtime.getValue

@Composable
fun BottomNavigationBar(
    navController: NavController,
    viewModel: CurrenciesViewModel
) {

    val items = listOf(
        Destinations.Currencies,
        Destinations.Exchange,
        Destinations.Transactions
    )
    val currentBackstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackstackEntry?.destination?.route

    NavigationBar {
        items.forEach {destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route){
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = findIcon(destination)), contentDescription = "")
                },
                label = {
                    Text(text = findLabel(destination))
                }
            )
        }
    }
}

private fun findIcon(destination: Destinations): Int {
    return when(destination){
        Destinations.Currencies -> R.drawable.currencies_ic
        Destinations.Exchange -> R.drawable.exchange_ic
        Destinations.Transactions -> R.drawable.transactions_ic
    }
}
private fun findLabel(destination: Destinations): String {
    return when(destination){
        Destinations.Currencies -> "Currencies"
        Destinations.Exchange -> "Exchange"
        Destinations.Transactions -> "Transactions"
    }
}