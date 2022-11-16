package com.ilazar.myapp.core

import android.content.Context
import android.util.Log
import com.ilazar.myapp.todo.data.ItemRepository
import com.ilazar.myapp.todo.data.local.AppDatabase
import com.ilazar.myapp.todo.data.remote.ItemService
import com.ilazar.myapp.todo.data.remote.ItemWsClient

class AppContainer(val context: Context) {
    init {
        Log.d(TAG, "init")
    }

    val itemService: ItemService = Api.retrofit.create(ItemService::class.java)
    val itemWsClient: ItemWsClient = ItemWsClient(Api.okHttpClient)

    val database: AppDatabase by lazy { AppDatabase.getDatabase(context) }

    val itemRepository: ItemRepository by lazy {
        ItemRepository(itemService, itemWsClient, database.itemDao())
    }
}
