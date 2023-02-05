package com.example.morhealth.data

import android.content.ContentValues
import android.content.Context
import com.example.morhealth.domain.Client

class ClientDAO(context: Context) : mySQLiteHelper(context) {

    private val INSERT_CLIENT = "INSERT INTO users (username, name, lastname_p, lastname_m, gender, age, email, password, premium) VALUES (username, name, lastname_p, lastname_m, gender, age, email, password, premium)"
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
            put("premium", user.premium)
        }

        val db = this.writableDatabase

        try {
            if (db.insert("users", null, data) == -1L) {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return true
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
                    cursor.getString(8),
                    cursor.getInt(9) == 1
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

    fun selectClients(): ArrayList<Client> {
        SELECT_CLIENT = "SELECT * FROM users"
        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_CLIENT, null)
        val users = ArrayList<Client>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val user = Client()
                    user.apply {
                        username = cursor.getString(1)
                        name = cursor.getString(2)
                        lastname_p = cursor.getString(3)
                        lastname_m = cursor.getString(4)
                        gender = cursor.getInt(5) == 1
                        age = cursor.getInt(6)
                        email = cursor.getString(7)
                        pswd = cursor.getString(8)
                        premium = cursor.getInt(9) == 1
                        users.add(user)
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }
        return users
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

    fun deleteAllRows() {
        val db = this.writableDatabase
        db.delete("users", null, null)
        db.close()
    }

}