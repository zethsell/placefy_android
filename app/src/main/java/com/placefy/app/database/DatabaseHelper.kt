package com.placefy.app.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "placefy", null, 1) {

    companion object {
        const val AUTH_TABLE = "auth"
        const val USER_TABLE = "users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        generateAuthTable(db)
        generateUsersTable(db)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    private fun generateAuthTable(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if NOT EXISTS `auth` (" +
                "id integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "`accessToken` text," +
                "`refreshToken` text," +
                "`keepConnected` boolean);"

        db?.execSQL(sql)
    }

    private fun generateUsersTable(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if NOT EXISTS `users` (" +
                "`id` integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "`name` varchar," +
                "`surname` varchar," +
                "`email` varchar NOT NULL UNIQUE," +
                "`personDoc` varchar," +
                "`registration` varchar," +
                "`creci`  varchar," +
                "`birthDate` varchar," +
                "`type` varchar NOT NULL," +
                "`accessAt` timestamp," +
                "`lastAccessAt` timestamp," +
                "`imgProfile` varchar," +
                "`imgProfileThumb` varchar," +
                "`phone` varchar);"
        db?.execSQL(sql)
    }
}