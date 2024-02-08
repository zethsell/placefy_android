package com.placefy.app.models.api.auth.signin

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String?
)
