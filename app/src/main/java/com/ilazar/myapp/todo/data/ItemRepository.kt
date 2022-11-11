package com.ilazar.myapp.todo.data

import android.util.Log
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.todo.data.remote.ItemService

class ItemRepository(private val itemService: ItemService) {
    init {
        Log.d(TAG, "init")
    }

    suspend fun loadAll(): List<Item> {
        Log.d(TAG, "loadAll")
        return itemService.find()
    }

    suspend fun update(item: Item): Item {
        Log.d(TAG, "update $item")
        return itemService.update(item.id, item)
    }

    suspend fun load(itemId: String?): Item {
        Log.d(TAG, "load $itemId")
        return itemService.read(itemId)
    }

    suspend fun save(text: String): Item {
        Log.d(TAG, "save $text")
        return itemService.create(Item(id = null, text = text))
    }
}