package com.bor96dev.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bor96dev.presentation.composables.BottomNavigationBar
import com.bor96dev.presentation.currencies.CurrenciesViewModel
import com.bor96dev.presentation.navigation.NavGraph
import com.bor96dev.presentation.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                val navController = rememberNavController()
                val currenciesViewModel: CurrenciesViewModel = hiltViewModel()

                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                        )
                    }

                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        currenciesViewModel = currenciesViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }
}