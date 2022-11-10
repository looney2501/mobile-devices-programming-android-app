package com.ilazar.myapp.todo.ui.items

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ilazar.myapp2.todo.data.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel : ViewModel() {
    companion object {
        val TAG = "ItemsViewModel"
    }

    private val _uiState = MutableStateFlow(
        ItemsUiState(ItemRepository.items)
    )
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "init")
    }
}
