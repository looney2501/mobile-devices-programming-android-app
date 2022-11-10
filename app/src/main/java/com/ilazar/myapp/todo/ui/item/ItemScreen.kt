package com.ilazar.myapp.todo.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ilazar.myapp.todo.ui.item.ItemViewModel
import com.ilazar.myapp.todo.ui.item.ItemViewModelFactory

@Composable
fun ItemScreen(itemId: String, onClose: () -> Unit) {
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModelFactory(itemId))
    val itemUiState by itemViewModel.uiState.collectAsState()
    var text by rememberSaveable { mutableStateOf(itemUiState.item?.text ?: "") }
    Log.d("ItemScreen", "recompose, text = $text")

    Row {
        TextField(value = text, onValueChange = { text = it }, label = { Text("Text") })
        Button(onClick = {
            Log.d("ItemScreen", "add item with text = $text");
            itemViewModel.updateItem(itemId, text)
            onClose()
        }) { Text("Add") }
    }
}


@Preview
@Composable
fun PreviewItemScreen() {
    ItemScreen(itemId = "0", onClose = {})
}
