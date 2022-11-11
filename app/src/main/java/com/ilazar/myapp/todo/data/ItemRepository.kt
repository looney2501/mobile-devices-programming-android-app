package com.ilazar.myapp.todo.data

import android.util.Log
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.todo.data.remote.ItemService

class ItemRepository(private val itemService: ItemService) {
    private var cachedItems: List<Item>? = null

    init {
        Log.d(TAG, "init")
    }

    suspend fun loadAll(): List<Item> {
        Log.d(TAG, "loadAll")
        val items = itemService.find();
        cachedItems = items
        return items
    }

    suspend fun load(itemId: String?): Item {
        Log.d(TAG, "load $itemId")
        return itemService.read(itemId)
    }

    suspend fun update(item: Item): Item {
        Log.d(TAG, "update $item")
        return itemService.update(item.id, item)
    }

    suspend fun save(item: Item): Item {
        Log.d(TAG, "save $item")
        return itemService.create(item)
    }

    fun getItem(itemId: String?): Item? = cachedItems?.find { it.id == itemId }
}