package com.example.medicationtrackerehb.core.nav_graph.bottom_nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medicationtrackerehb.core.nav_graph.HOME_ROUTE
import com.example.medicationtrackerehb.core.nav_graph.Screen
import com.example.medicationtrackerehb.core.nav_graph.addNavGraph
import com.example.medicationtrackerehb.core.nav_graph.homeNavGraph
import com.example.medicationtrackerehb.presentation.inventory.InventoryScreen
import com.example.medicationtrackerehb.presentation.reminder.ReminderHomeScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
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

        addNavGraph(navController)
        homeNavGraph(navController)
    }
}