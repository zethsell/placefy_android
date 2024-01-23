package com.placefy.app.database.contracts

import com.placefy.app.models.user.User

interface IUserDAO {
    fun save(user: User): Boolean

    fun me(): User?

    fun clean()

}