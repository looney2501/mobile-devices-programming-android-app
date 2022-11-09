package com.ilazar.myapp.todo.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview

val TAG = "ItemAdd"

@Composable
fun ItemAdd() {
    var text by rememberSaveable { mutableStateOf("") }
    Log.d(TAG, "recompose, text = $text")
    Row {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text") }
        )
        Button(onClick = {
            Log.d(TAG, "add todo");
        }) {
            Text("Add")
        }
    }
}


@Preview
@Composable
fun PreviewItemAdd() {
    ItemAdd()
}
