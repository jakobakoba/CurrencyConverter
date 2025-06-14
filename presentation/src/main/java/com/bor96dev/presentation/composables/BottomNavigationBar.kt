package com.bor96dev.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bor96dev.presentation.MainViewModel
import com.bor96dev.presentation.R
import com.bor96dev.presentation.navigation.Destinations

@Composable
fun BottomNavigationBar(
    navController: NavController,
    viewModel: MainViewModel
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
                }
            )
        }
    }
}

private fun findIcon(destination: Destinations): ImageVector{
    return when(destination){
        Destinations.Currencies -> Icons.Filled
        Destinations.Exchange -> Icons
        Destinations.Transactions ->

    }
}