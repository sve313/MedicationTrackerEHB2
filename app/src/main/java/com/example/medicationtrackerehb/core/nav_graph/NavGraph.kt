package com.example.medicationtrackerehb.core.nav_graph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.medicationtrackerehb.presentation.reminder_details.ReminderDetailsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startRoute : String,
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,
        route = ROOT_ROUTE
    ) {
        addNavGraph(navController)
        homeNavGraph(navController)
        composable(
            route = Screen.MedicineDetails.route,
            arguments = listOf(
                navArgument(DETAIL_ARGUMENT_REMINDER) {
                    type = NavType.IntType
                }
            )
        ) {
            Log.d("reminder it", it.arguments?.getInt(DETAIL_ARGUMENT_REMINDER).toString())
            ReminderDetailsScreen(
                navController = navController,
            )
        }
    }
}