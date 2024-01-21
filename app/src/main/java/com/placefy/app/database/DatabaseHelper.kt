package com.placefy.app.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "placefy", null, 1) {

    companion object {
        const val AUTH_TABLE = "auth"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if NOT EXISTS `auth` (" +
                "id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "`accessToken` text," +
                "`refreshToken` text," +
                "`keepConnected` boolean);"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}