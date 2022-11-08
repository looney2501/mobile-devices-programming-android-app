package com.ilazar.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ilazar.myapp.todo.data.Item
import com.ilazar.myapp.todo.ui.ItemDetail

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("MainActivity", "onCreate")
            MyApp {
                ItemDetail(Item(text = "Learn android", done = false))
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Log.d("MyApp", "recompose")
    content()
}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp {
        ItemDetail(Item(text = "Learn android", done = false))
    }
}
