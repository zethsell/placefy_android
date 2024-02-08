package com.placefy.app.models.api.auth.signin

data class SignInRequest(
    val email: String,
    val password: String,
    val keepConnected: Boolean = false
)
