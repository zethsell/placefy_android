package com.placefy.app.api.interfaces

import com.placefy.app.models.api.auth.refresh.RefreshRequest
import com.placefy.app.models.api.auth.refresh.RefreshResponse
import com.placefy.app.models.api.auth.signin.SignInRequest
import com.placefy.app.models.api.auth.signin.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("auth/sign-in")
    suspend fun signIn(@Body data: SignInRequest): Response<SignInResponse>

    @POST("auth/refresh-token")
    suspend fun refresh(@Body data: RefreshRequest): Response<RefreshResponse>
}