package com.placefy.app.api.interfaces

import com.placefy.app.models.data.user.User
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @GET("v1/me")
    suspend fun me(): Response<User>
}