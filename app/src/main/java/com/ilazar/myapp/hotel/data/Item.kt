package com.ilazar.myapp.hotel.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(@PrimaryKey val _id: String = "",
                val name: String = "",
                val capacity: Int = 0,
                val isAvailable: Boolean = false,
                val dateRegistered: String = Date().toString().substring(0, 10))
