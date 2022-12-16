package com.ilazar.myapp.hotel.data.remote

import com.ilazar.myapp.hotel.data.Item
import retrofit2.http.*

interface ItemService {
    @GET("/api/hotels")
    suspend fun find(): List<Item>

    @GET("/api/hotels/{id}")
    suspend fun read(@Path("id") itemId: String?): Item;

    @Headers("Content-Type: application/json")
    @POST("/api/hotels")
    suspend fun create(@Body item: Item): Item

    @Headers("Content-Type: application/json")
    @PUT("/api/hotels/{id}")
    suspend fun update(@Path("id") itemId: String?, @Body item: Item): Item
}
