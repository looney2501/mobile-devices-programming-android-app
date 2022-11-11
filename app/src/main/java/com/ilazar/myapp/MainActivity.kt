package com.ilazar.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.todo.ui.ItemScreen
import com.ilazar.myapp.todo.ui.items.ItemsScreen
import com.ilazar.myapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "onCreate")
            MyApp {
                MyAppNavHost()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Log.d("MyApp", "recompose")
    MyAppTheme {
        Surface {
            content()
        }
    }
}

val itemsRoute = "items"

@Composable
fun MyAppNavHost() {
    val navController = rememberNavController()
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
                itemId = it.arguments?.getString("id")!!,
                onClose = {
                    Log.d("MyAppNavHost", "navigate back to list")
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp {
        MyAppNavHost()
    }
}
