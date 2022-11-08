package com.ilazar.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("MainActivity", "onCreate")
            ItemDetail("Learn compose")
        }
    }
}

@Composable
fun ItemDetail(text: String) {
    Log.d("ItemDetail", "recompose")
    Text(text = text)
}

@Preview
@Composable
fun PreviewItemDetail() {
    ItemDetail("Learn android")
}
