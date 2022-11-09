package com.ilazar.myapp.todo.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemAdd(itemsViewModel: ItemsViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    Log.d("ItemAdd", "recompose, text = $text")

    Row {
        TextField(value = text, onValueChange = { text = it }, label = { Text("Text") })
        Button(onClick = {
            Log.d("ItemAdd", "add item with text = $text");
            itemsViewModel.addItem(text)
        }) { Text("Add") }
    }
}


@Preview
@Composable
fun PreviewItemAdd() {
    ItemAdd(ItemsViewModel())
}
