package com.ilazar.myapp.todo.ui.items

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ItemsScreen(onItemClick: (id: String) -> Unit) {
    Log.d("TodoScreen", "recompose")
    val itemsViewModel = viewModel<ItemsViewModel>()
    val itemsUiState by itemsViewModel.uiState.collectAsState()
    ItemList(
        itemList = itemsUiState.items,
        onItemClick = onItemClick,
    )
}

@Preview
@Composable
fun PreviewItemsScreen() {
    ItemsScreen(onItemClick = {})
}
