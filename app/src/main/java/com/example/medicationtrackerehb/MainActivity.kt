package com.example.medicationtrackerehb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.medicationtrackerehb.core.nav_graph.HOME_ROUTE
import com.example.medicationtrackerehb.core.nav_graph.SetupNavGraph
import com.example.medicationtrackerehb.ui.theme.MedicationTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicationTrackerTheme {
                SetupNavGraph(
                    navController = rememberNavController(),
                    startRoute = HOME_ROUTE
                )
            }
        }
    }

}