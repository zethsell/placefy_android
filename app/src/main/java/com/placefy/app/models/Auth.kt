package com.placefy.app.models

data class Auth(
    val id: Int,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val keepConnected: Boolean? = false
)
