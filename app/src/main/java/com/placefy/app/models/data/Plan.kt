package com.placefy.app.models.data

data class Plan(
    val id: Int?,
    val description: String?,
    val type: String?,
    val value: Double?,
    val numberOfProperties: Int?,
    val numberOfImages: Int?,
    val active: Boolean?,
    val period: Int?
)
