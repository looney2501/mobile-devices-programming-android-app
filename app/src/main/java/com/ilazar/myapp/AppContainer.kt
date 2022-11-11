package com.ilazar.myapp.core

import android.util.Log
import com.ilazar.myapp.todo.data.ItemRepository
import com.ilazar.myapp.todo.data.remote.ItemService

class AppContainer {
    init {
        Log.d(TAG, "init")
    }

    val itemService: ItemService = Api.retrofit.create(ItemService::class.java)

    val itemRepository: ItemRepository by lazy {
        ItemRepository(itemService)
    }
}
