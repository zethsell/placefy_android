package com.placefy.app.api.interfaces

import com.placefy.app.models.data.Property
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PropertyAPI {
    @GET("properties-public")
    suspend fun list(): Response<Array<Property>>

    @GET("properties-public/{id}")
    suspend fun show(@Path("id") id: Int): Response<Property>


}