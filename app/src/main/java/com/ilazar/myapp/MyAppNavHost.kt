package com.ilazar.myapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilazar.myapp.auth.LoginScreen
import com.ilazar.myapp.core.data.remote.Api
import com.ilazar.myapp.core.ui.UserPreferencesViewModel
import com.ilazar.myapp.hotel.ui.ItemScreen
import com.ilazar.myapp.hotel.ui.items.ItemsScreen

val hotelsRoute = "hotels"
val authRoute = "auth"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
    val onCloseItem = {
        Log.d("MyAppNavHost", "navigate back to list")
        navController.popBackStack()
    }
    val userPreferencesViewModel =
        viewModel<UserPreferencesViewModel>(factory = UserPreferencesViewModel.Factory)
    val userPreferencesUiState = userPreferencesViewModel.uiState
    val myAppViewModel = viewModel<MyAppViewModel>(factory = MyAppViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = authRoute
    ) {
        composable(hotelsRoute) {
            ItemsScreen(
                onItemClick = { itemId ->
                    Log.d("MyAppNavHost", "navigate to hotel $itemId end")
                    navController.navigate("$hotelsRoute/$itemId")
                },
                onAddItem = {
                    Log.d("MyAppNavHost", "navigate to new hotel")
                    navController.navigate("$hotelsRoute-new")
                },
                onLogout = {
                    Log.d("MyAppNavHost", "logout")
                    myAppViewModel.logout()
                    Api.tokenInterceptor.token = null
                    navController.navigate(authRoute) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(
            route = "$hotelsRoute/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        )
        {
            ItemScreen(
                itemId = it.arguments?.getString("id"),
                onClose = { onCloseItem() }
            )
        }
        composable(route = "$hotelsRoute-new")
        {
            ItemScreen(
                itemId = null,
                onClose = { onCloseItem() }
            )
        }
        composable(route = authRoute)
        {
            LoginScreen(
                onClose = {
                    Log.d("MyAppNavHost", "navigate to list")
                    navController.navigate(hotelsRoute)
                }
            )
        }
    }

    LaunchedEffect(userPreferencesUiState.token) {
        if (userPreferencesUiState.token.isNotEmpty()) {
            Log.d("MyAppNavHost", "Lauched effect navigate to hotels")
            Api.tokenInterceptor.token = userPreferencesUiState.token
            myAppViewModel.setToken(userPreferencesUiState.token)
            navController.navigate(hotelsRoute) {
                popUpTo(0)
            }
        }
    }
}
