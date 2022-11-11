package com.ilazar.myapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilazar.myapp.todo.ui.ItemScreen
import com.ilazar.myapp.todo.ui.items.ItemsScreen

val itemsRoute = "items"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val onCloseItem = {
        Log.d("MyAppNavHost", "navigate back to list")
        navController.popBackStack()
    }
    NavHost(
        navController = navController,
        startDestination = itemsRoute
    ) {
        composable(itemsRoute) {
            ItemsScreen(
                onItemClick = { itemId ->
                    Log.d("MyAppNavHost", "navigate to item $itemId")
                    navController.navigate("$itemsRoute/$itemId")
                }
            )
        }
        composable(
            route = "$itemsRoute/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        )
        {
            ItemScreen(
                itemId = it.arguments?.getString("id"),
                onClose = { onCloseItem() }
            )
        }
        composable(
            route = "$itemsRoute-new",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        )
        {
            ItemScreen(
                itemId = null,
                onClose = { onCloseItem() }
            )
        }
    }
}
