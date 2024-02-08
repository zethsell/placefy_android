package com.placefy.app.models.api.auth.refresh

import com.google.gson.annotations.SerializedName

data class RefreshResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String?
)
