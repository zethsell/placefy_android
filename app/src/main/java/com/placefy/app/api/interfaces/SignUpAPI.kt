package com.placefy.app.api.interfaces

import com.placefy.app.models.api.auth.signup.SignUpAgencyRequest
import com.placefy.app.models.api.auth.signup.SignUpAgentRequest
import com.placefy.app.models.api.auth.signup.SignUpParticularRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpAPI {
    @POST("auth/sign-up")
    suspend fun signUp(@Body data: SignUpParticularRequest): Response<Void>

    @POST("auth/sign-up/agent")
    suspend fun signUpAgent(@Body data: SignUpAgentRequest): Response<Void>

    @POST("auth/sign-up/agency")
    suspend fun signUpAgency(@Body data: SignUpAgencyRequest): Response<Void>
}