package com.ilazar.myapp.todo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ilazar.myapp.todo.data.Item

class ItemsViewModel : ViewModel() {
    init {
        Log.d("ItemsViewModel", "init")
    }

    val items = listOf<Item>(
        Item(text = "Learn android", done = false),
        Item(text = "Learn compose", done = false)
    )
}
