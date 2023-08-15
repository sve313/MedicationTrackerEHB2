package com.example.medicationtrackerehb.core.nav_graph

const val ROOT_ROUTE = "root"
const val HOME_ROUTE = "home"
const val DETAILS_ROUTE = "details"
const val ADD_EDIT_ROUTE = "add"

const val DETAIL_ARGUMENT_REMINDER = "reminder"

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddMedicine : Screen("add_medicine_screen")

    object Inventory : Screen("inventory_screen")
    object MedicineDetails : Screen("medicine_details_screen/{$DETAIL_ARGUMENT_REMINDER}") {
        fun passId(reminder: Int ): String{
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_REMINDER}", newValue = reminder.toString())
        }
    }
}