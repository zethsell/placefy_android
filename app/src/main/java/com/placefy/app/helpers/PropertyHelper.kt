package com.placefy.app.helpers

import com.placefy.app.models.data.Property

class PropertyHelper(val property: Property) {

    fun composeAddress(): String {
        var address = property.address?.addressLine ?: ""
        address = address.plus(", ").plus(property.address?.number ?: "S/N")

        if (property.address?.complement != "") {
            address = address.plus(", ").plus(property.address?.complement)
        }

        address = address.plus(" - ").plus(property.address?.suburb ?: "")
        address = address.plus(", ").plus(property.address?.city ?: "")
        return address.plus(" - ").plus(property.address?.state ?: "")
    }

    fun composeTotalValue(): String {
        var value: Double = 0.00

        if (property.priceInformation != null) {
            property.priceInformation.forEach { price ->
                if (price.addToTotal) {
                    value += price.value
                }
            }
        }

        return value.toString()
    }

    fun composeAmenities(): Map<String, String> {
        var totalArea = 0
        var bedrooms = 0
        var cars = 0
        var bathrooms = 0

        if (property.amenities != null) {
            property.amenities.forEach { amenity ->
                when (amenity.name) {
                    "totalArea" -> totalArea = amenity.quantity ?: 0
                    "bedrooms" -> bedrooms = amenity.quantity ?: 0
                    "cars" -> cars = amenity.quantity ?: 0
                    "bathrooms" -> bathrooms = amenity.quantity ?: 0
                }
            }
        }

        return mapOf<String, String>(
            "totalArea" to totalArea.toString(),
            "bedrooms" to bedrooms.toString(),
            "cars" to cars.toString(),
            "bathrooms" to bathrooms.toString()
        )
    }
}