package com.ilazar.myapp.todo.data.remote

import com.ilazar.myapp.todo.data.Item

data class Payload(val item: Item)
data class ItemEvent(val event: String, val payload: Payload)
