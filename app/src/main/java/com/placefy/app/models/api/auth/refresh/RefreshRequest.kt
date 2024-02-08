package com.placefy.app.models.api.auth.refresh

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("refresh_token")
    val refreshToken: String,
)
