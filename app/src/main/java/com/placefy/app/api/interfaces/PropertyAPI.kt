package com.placefy.app.api.interfaces

import com.placefy.app.models.data.Property
import retrofit2.Response
import retrofit2.http.GET

interface PropertyAPI {
    @GET("properties-public")
    suspend fun list(): Response<Array<Property>>


}