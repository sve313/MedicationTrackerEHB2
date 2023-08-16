package com.example.medicationtrackerehb.core.nav_graph.bottom_nav_graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.medicationtrackerehb.core.nav_graph.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,

) {

    object Home : BottomBarScreen(
        route = Screen.Home.route,
        title = "Home",
        icon = Icons.Default.Home
    )

    object Inventory : BottomBarScreen(
        route = Screen.Inventory.route,
        title = "inventory",
        icon = Icons.Default.Healing
    )



}
