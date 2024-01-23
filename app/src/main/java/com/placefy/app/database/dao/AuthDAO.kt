package com.placefy.app.database.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.placefy.app.database.DatabaseHelper
import com.placefy.app.database.contracts.IAuthDAO
import com.placefy.app.models.Auth

class AuthDAO(context: Context) : IAuthDAO {

    private val read: SQLiteDatabase = DatabaseHelper(context).readableDatabase
    private val write: SQLiteDatabase = DatabaseHelper(context).writableDatabase
    override fun save(auth: Auth): Boolean {
        val sql =
            "INSERT INTO ${DatabaseHelper.AUTH_TABLE} (id,accessToken,refreshToken, keepConnected) " +
                    "VALUES (1,'Bearer ${auth.accessToken}', '${auth.refreshToken}', ${auth.keepConnected}) " +
                    "ON CONFLICT(id) DO UPDATE " +
                    "SET accessToken='Bearer ${auth.accessToken}', " +
                    "refreshToken = '${auth.refreshToken}'," +
                    "keepConnected = ${auth.keepConnected};"

        write.execSQL(sql)
        return true
    }

    override fun show(): Auth {
        val sql = "SELECT * FROM ${DatabaseHelper.AUTH_TABLE} WHERE id = 1;"
        val cursor = read.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val accessTokenIndex = cursor.getColumnIndex("accessToken")
            val refreshTokenIndex = cursor.getColumnIndex("refreshToken")
            val keepConnectedIndex = cursor.getColumnIndex("keepConnected")

            val auth = Auth(
                1,
                cursor.getString(accessTokenIndex),
                cursor.getString(refreshTokenIndex),
                cursor.getShort(keepConnectedIndex) > 0
            )

            cursor.close()
            return auth
        }

        cursor.close()
        return Auth(1)
    }

    override fun clean() {
        val sql = "DELETE FROM auth"
        write.execSQL(sql)
    }
}