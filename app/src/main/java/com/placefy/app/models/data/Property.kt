package com.placefy.app.models.data

import com.placefy.app.models.data.user.User

data class Property(
    val id: Int?,
    val description: String?,
    val title: String?,
    val type: String?,
    val amenities: Array<Amenity>?,
    val priceInformation: Array<PriceInformation>?,
    val gallery: Array<Image>?,
    val address: Address?,
    val user: User?
) {

}
