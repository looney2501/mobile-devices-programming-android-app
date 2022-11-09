package com.ilazar.myapp.todo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ilazar.myapp.todo.data.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel : ViewModel() {
    companion object {
        val TAG = "ItemsViewModel"
    }

    private val _uiState = MutableStateFlow(
        ItemsUiState(
            items = listOf<Item>(
                Item(id = 0, text = "Learn android", done = false),
                Item(id = 1, text = "Learn compose", done = false)
            )
        )
    )
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "init")
    }

    fun addItem(text: String) {
        Log.d(TAG, "addItem $text")
        val items = _uiState.value.items;
        val item = Item(items.size + 1, text, false)
        _uiState.value = ItemsUiState(items.plus(item))
    }

    fun toggleItemDone(id: Int) {
        Log.d(TAG, "toggleItemDone $id")
        val items = _uiState.value.items;
        _uiState.value =
            ItemsUiState(
                items.map {
                    if (it.id == id) Item(it.id, it.text, !it.done) else it
                }
            )
    }
}
