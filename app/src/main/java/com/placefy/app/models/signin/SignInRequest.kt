package com.placefy.app.models.signin

data class SignInRequest(
    val email: String,
    val password: String,
    val keepConnected: Boolean = false
)
