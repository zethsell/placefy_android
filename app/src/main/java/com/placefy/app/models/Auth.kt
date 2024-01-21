package com.placefy.app.models

data class Auth(
    val id: Int,
    val accessToken: String? = "",
    val refreshToken: String? = "",
    val keepConnected: Boolean? = false
)
