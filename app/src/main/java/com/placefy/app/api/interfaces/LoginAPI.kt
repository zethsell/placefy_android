package com.placefy.app.api.interfaces

import com.placefy.app.models.signin.SignInRequest
import com.placefy.app.models.signin.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("auth/sign-in")
    suspend fun signIn(@Body data: SignInRequest): Response<SignInResponse>
}