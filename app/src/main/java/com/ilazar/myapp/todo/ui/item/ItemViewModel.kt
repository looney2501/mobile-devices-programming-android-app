package com.ilazar.myapp.todo.ui.item

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ilazar.myapp.MyApplication
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.todo.data.Item
import com.ilazar.myapp.todo.data.ItemRepository
import kotlinx.coroutines.launch

sealed interface ItemUiState {
    object Idle : ItemUiState
    object Saving : ItemUiState
    data class Success(val item: Item) : ItemUiState
    data class Error(val exception: Exception) : ItemUiState
}

class ItemViewModel(private val itemId: String?, private val itemRepository: ItemRepository) :
    ViewModel() {
    var uiState: ItemUiState by mutableStateOf(ItemUiState.Idle)
        private set

    init {
        Log.d(TAG, "init")
    }

    fun createItem(text: String) {
        Log.d(TAG, "createItem")
        viewModelScope.launch {
            uiState = ItemUiState.Saving
            uiState = try {
                ItemUiState.Success(itemRepository.save(text))
            } catch (e: Exception) {
                ItemUiState.Error(e)
            }
        }
    }

    fun createItem(itemId: String, text: String) {
        TODO("Not yet implemented")
    }

    companion object {
        fun Factory(itemId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                ItemViewModel(itemId, app.container.itemRepository)
            }
        }
    }
}
