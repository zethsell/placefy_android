package com.placefy.app.api.interfaces

import com.placefy.app.models.data.Plan
import retrofit2.Response
import retrofit2.http.GET

interface PlanAPI {
    @GET("plans")
    suspend fun listAdmin(): Response<Array<Plan>>

}