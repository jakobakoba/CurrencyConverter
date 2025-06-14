package com.bor96dev.presentation.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy // Для проверки иерархии маршрутов
import androidx.navigation.compose.currentBackStackEntryAsState
// import com.bor96dev.presentation.currencies.CurrenciesViewModel // Убираем, если не используется
import com.bor96dev.presentation.R
import com.bor96dev.presentation.navigation.Destinations // Используем обновленный Destinations

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        Destinations.Currencies,
        Destinations.Exchange,
        Destinations.Transactions
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = findIcon(screen)),
                        contentDescription = findLabel(screen)
                    )
                },
                label = { Text(findLabel(screen)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.routeDefinition } == true,
                onClick = {
                    navController.navigate(screen.routeDefinition) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun findIcon(destination: Destinations): Int {
    return when (destination) {
        is Destinations.Currencies -> R.drawable.currencies_ic
        is Destinations.Exchange -> R.drawable.exchange_ic
        is Destinations.Transactions -> R.drawable.transactions_ic
    }
}

private fun findLabel(destination: Destinations): String {
    return when (destination) {
        is Destinations.Currencies -> "Валюты"
        is Destinations.Exchange -> "Обмен"
        is Destinations.Transactions -> "История"
    }
}