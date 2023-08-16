package com.example.medicationtrackerehb.core.nav_graph


import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.medicationtrackerehb.presentation.inventory.InventoryScreen
import com.example.medicationtrackerehb.presentation.reminder.ReminderHomeScreen


fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Home.route,
        route = HOME_ROUTE
    ) {


        composable(
            route = Screen.Home.route
        ) {
            ReminderHomeScreen(
                navController
            )
        }

        composable(
            route = Screen.Inventory.route
        ) {
            InventoryScreen(
                navController
            )
        }
    }
}