package com.ilazar.myapp.hotel.data.remote

import com.ilazar.myapp.hotel.data.Item

data class ItemEvent(val type: String, val payload: Item)
