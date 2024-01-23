package com.placefy.app.database.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.placefy.app.database.DatabaseHelper
import com.placefy.app.database.contracts.IUserDAO
import com.placefy.app.models.user.User

class UserDAO(context: Context) : IUserDAO {

    private val read: SQLiteDatabase = DatabaseHelper(context).readableDatabase
    private val write: SQLiteDatabase = DatabaseHelper(context).writableDatabase
    override fun save(user: User): Boolean {
        val sql = "INSERT INTO ${DatabaseHelper.USER_TABLE} (" +
                "id, name, surname, email, personDoc, registration, creci," +
                "birthDate, type, accessAt, lastAccessAt, phone)" +
                "VALUES ( ${user.id}, '${user.name}', '${user.surname}', '${user.email}', '${user.personDoc}'," +
                "'${user.registration}', '${user.creci}', '${user.birthDate}', '${user.type}', '${user.accessAt}'," +
                "'${user.lastAccessAt}', '${user.phone}') " +
                "ON CONFLICT(id) DO UPDATE " +
                "SET " +
                "id = ${user.id} ," +
                "name = '${user.name}' ," +
                "surname = '${user.surname}' ," +
                "email = '${user.email}' ," +
                "personDoc = '${user.personDoc}' ," +
                "registration = '${user.registration}' ," +
                "creci = '${user.creci}' ," +
                "birthDate = '${user.birthDate}' ," +
                "type = '${user.type}' ," +
                "accessAt = '${user.accessAt}' ," +
                "lastAccessAt = '${user.lastAccessAt}' ," +
                "phone = '${user.phone}';"

        write.execSQL(sql)
        return true
    }

    override fun clean() {
        val sql = "DELETE FROM users"
        write.execSQL(sql)
    }

    override fun me(): User? {
        val sql = "SELECT * FROM ${DatabaseHelper.USER_TABLE} LIMIT 1;"
        val cursor = read.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex("id")
            val nameIndex = cursor.getColumnIndex("name")
            val surnameIndex = cursor.getColumnIndex("surname")
            val emailIndex = cursor.getColumnIndex("email")
            val personDocIndex = cursor.getColumnIndex("personDoc")
            val registrationIndex = cursor.getColumnIndex("registration")
            val creciIndex = cursor.getColumnIndex("creci")
            val birthDateIndex = cursor.getColumnIndex("birthDate")
            val typeIndex = cursor.getColumnIndex("type")
            val accessAtIndex = cursor.getColumnIndex("accessAt")
            val lastAccessAtIndex = cursor.getColumnIndex("lastAccessAt")
            val phoneIndex = cursor.getColumnIndex("phone")

            val user = User(
                cursor.getInt(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(surnameIndex),
                cursor.getString(emailIndex),
                cursor.getString(phoneIndex),
                cursor.getString(personDocIndex),
                cursor.getString(registrationIndex),
                cursor.getString(creciIndex),
                cursor.getString(birthDateIndex),
                cursor.getString(typeIndex),
                cursor.getString(accessAtIndex),
                cursor.getString(lastAccessAtIndex),
            )

            cursor.close()
            return user
        }

        cursor.close()
        return null
    }
}