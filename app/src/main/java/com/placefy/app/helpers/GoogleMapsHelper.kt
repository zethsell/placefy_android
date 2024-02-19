package com.placefy.app.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.placefy.app.models.data.Address

class GoogleMapsHelper(
    private val context: Context,
    private val address: Address
) {
    fun showLocation() {
        startActivity("http://www.google.com/maps/place/")
    }

    fun showRoute() {
        startActivity("google.navigation:q=")
    }

    private fun startActivity(baseUrl: String) {
        val uri = baseUrl.plus(composeSearch())
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uri)
        )
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(context, intent, null)
    }

    private fun composeSearch(): String {
        var search = ""
        val addressLine = this.concatArgs(address.addressLine ?: "")
        val number = this.concatArgs(address.number ?: "")
        val city = concatArgs(address.city.plus(" - ${address.state}"))
        search = search.plus(addressLine).plus(number).plus(city).plus(address.zipcode)
        return search
    }

    private fun concatArgs(addressPart: String): String {
        var partSearch = ""

        val splitAddress = addressPart.split(" ")
        splitAddress.forEach { part ->
            if (part == splitAddress.last()) {
                partSearch = partSearch.plus("$part,+")

            } else {
                partSearch = partSearch.plus("$part+")
            }
        }

        return partSearch
    }

}


