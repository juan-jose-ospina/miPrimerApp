package com.example.miprimerapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private const val CALCULATOR_ROUTE = "calculator"
private const val HISTORY_ROUTE = "history"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: CalculatorViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = CALCULATOR_ROUTE
    ) {
        composable(CALCULATOR_ROUTE) {
            ShowCalculator(
                viewModel = viewModel,
                onHistoryClick = { navController.navigate(HISTORY_ROUTE) }
            )
        }

        composable(HISTORY_ROUTE) {
            ShowHistory(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}