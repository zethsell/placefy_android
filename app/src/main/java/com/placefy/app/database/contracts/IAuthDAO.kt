package com.placefy.app.database.contracts

import com.placefy.app.models.Auth

interface IAuthDAO {
    fun save(auth: Auth): Boolean

    fun show(): Auth

    fun clean()
}