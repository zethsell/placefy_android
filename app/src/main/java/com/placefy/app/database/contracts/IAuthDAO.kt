package com.placefy.app.database.contracts

import com.placefy.app.models.data.Auth

interface IAuthDAO {
    fun save(auth: Auth): Boolean

    fun show(): Auth

    fun clean()
}