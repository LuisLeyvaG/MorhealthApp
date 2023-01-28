package com.example.morhealth.data

import android.content.ContentValues
import android.content.Context
import com.example.morhealth.domain.Client

class ClientDAO(context: Context) : mySQLiteHelper(context) {

    private val INSERT_CLIENT = "INSERT INTO users (username, name, lastname_p, lastname_m, gender, age, email, password) VALUES (username, name, lastname_p, lastname_m, gender, age, email, password)"
    private lateinit var SELECT_CLIENT: String

    fun insertClient(user: Client): Boolean {
        val data = ContentValues()
        data.apply {
            put("username", user.username)
            put("name", user.name)
            put("lastname_p", user.lastname_p)
            put("lastname_m", user.lastname_m)
            put("gender", user.gender)
            put("age", user.age)
            put("email", user.email)
            put("password", user.pswd)
        }

        val db = this.writableDatabase

        var result = true

        try {
            db.insert("users", null, data)
        } catch (e: Exception) {
            e.printStackTrace()
            result = false
        } finally {
            db.close()
            return result
        }
    }

    fun selectClient(username: String): Client? {
        SELECT_CLIENT = "SELECT * FROM users WHERE username = '$username'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_CLIENT, null)

        try {
            if (cursor.moveToFirst()) {
                return Client(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5) == 1,
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }
        return null
    }

    fun validateLogin(username: String, password: String): Boolean {
        SELECT_CLIENT = "SELECT * FROM users WHERE username = '$username' AND password = '$password'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_CLIENT, null)

        try {
            if (cursor.moveToFirst()) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }
        return false
    }

}