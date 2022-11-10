package com.ilazar.myapp.todo.ui.item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilazar.myapp2.todo.data.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemViewModelFactory(private val itemId: String?) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ItemViewModel(itemId) as T
}

class ItemViewModel(itemId: String?) : ViewModel() {
    companion object {
        val TAG = "ItemEditViewModel"
    }

    private val _uiState =
        MutableStateFlow(ItemUiState(ItemRepository.items.filter { it.id == itemId }[0]))
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "init")
    }

    fun updateItem(id: String, text: String) {
        Log.d(TAG, "addItem $text")
        val item = ItemRepository.update(id, text)
        _uiState.value = ItemUiState(item)
    }
}
