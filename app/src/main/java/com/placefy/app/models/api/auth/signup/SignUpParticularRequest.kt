package com.placefy.app.models.api.auth.signup

import com.placefy.app.models.data.Address

data class SignUpParticularRequest(
    val name: String?,
    val surname: String?,
    val email: String?,
    val phone: String?,
    val personDoc: String?,
    val registration: String?,
    val birthDate: String?,
    val password: String?,
    val passwordConfirmation: String,
    val address: Address
)
