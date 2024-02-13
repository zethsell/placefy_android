package com.placefy.app.models.api.auth.signup

import com.placefy.app.models.data.Address

data class SignUpAgentRequest(
    val name: String?,
    val surname: String?,
    val email: String?,
    val phone: String?,
    val personDoc: String?,
    val registration: String?,
    val creci: String?,
    val birthDate: String?,
    val address: Address,
)
