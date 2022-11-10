package com.ilazar.myapp.todo.ui.items

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ilazar.myapp.R

@Composable
fun ItemsScreen(onItemClick: (id: String) -> Unit) {
    Log.d("ItemsScreen", "recompose")
    val itemsViewModel = viewModel<ItemsViewModel>()
    val itemsUiState by itemsViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.items)) })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d("ItemsScreen", "todo")
                },
            ) { Icon(Icons.Rounded.Add, "Add") }
        }
    ) {
        ItemList(
            itemList = itemsUiState.items,
            onItemClick = onItemClick,
        )
    }
}

@Preview
@Composable
fun PreviewItemsScreen() {
    ItemsScreen(onItemClick = {})
}
