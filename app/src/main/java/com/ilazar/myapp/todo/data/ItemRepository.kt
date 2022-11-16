package com.ilazar.myapp.todo.data

import android.util.Log
import com.ilazar.myapp.core.TAG
import com.ilazar.myapp.todo.data.remote.ItemEvent
import com.ilazar.myapp.todo.data.remote.ItemService
import com.ilazar.myapp.todo.data.remote.ItemWsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ItemRepository(private val itemService: ItemService, private val itemWsClient: ItemWsClient) {
    private var items: List<Item> = listOf();

    private var itemsFlow: MutableSharedFlow<Result<List<Item>>> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val itemStream: SharedFlow<Result<List<Item>>> = itemsFlow

    init {
        Log.d(TAG, "init")
    }

    suspend fun refresh() {
        Log.d(TAG, "refresh started")
        try {
            items = itemService.find()
            Log.d(TAG, "refresh succeeded")
            itemsFlow.emit(Result.success(items))
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
            itemsFlow.emit(Result.failure(e))
        }
    }

    suspend fun openWsClient() {
        Log.d(TAG, "openWsClient")
        withContext(Dispatchers.IO) {
            getItemEvents().collect {
                Log.d(TAG, "Item event collected $it")
                if (it.isSuccess) {
                    val itemEvent = it.getOrNull();
                    when (itemEvent?.event) {
                        "created" -> handleItemCreated(itemEvent.payload.item)
                        "updated" -> handleItemUpdated(itemEvent.payload.item)
                        "deleted" -> handleItemDeleted(itemEvent.payload.item)
                    }
                }
            }
        }
    }

    suspend fun closeWsClient() {
        Log.d(TAG, "closeWsClient")
        withContext(Dispatchers.IO) {
            itemWsClient.closeSocket()
        }
    }

    suspend fun getItemEvents(): Flow<Result<ItemEvent>> = callbackFlow {
        Log.d(TAG, "getItemEvents started")
        itemWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                if (it != null) {
                    Log.d(TAG, "onEvent trySend $it")
                    trySend(Result.success(it))
                }
            },
            onClosed = { close() },
            onFailure = { close() });
        awaitClose { itemWsClient.closeSocket() }
    }

    suspend fun update(item: Item): Item {
        Log.d(TAG, "update $item...")
        val updatedItem = itemService.update(item.id, item)
        Log.d(TAG, "update $item succeeded")
        handleItemUpdated(updatedItem)
        return updatedItem
    }

    suspend fun save(item: Item): Item {
        Log.d(TAG, "save $item...")
        val createdItem = itemService.create(item)
        Log.d(TAG, "save $item succeeded")
        handleItemCreated(createdItem)
        return createdItem
    }

    private suspend fun handleItemDeleted(item: Item) {
        Log.d(TAG, "handleItemDeleted - todo $item")
    }

    private suspend fun handleItemUpdated(item: Item) {
        Log.d(TAG, "handleItemUpdated...")
        items = items.map { if (it.id == item.id) item else it }
        itemsFlow.emit(Result.success(items))
    }

    private suspend fun handleItemCreated(item: Item) {
        Log.d(TAG, "handleItemCreated...")
        items = items.plus(item)
        itemsFlow.emit(Result.success(items))
    }
}