package com.example.morhealth.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.morhealth.domain.Client

open class mySQLiteHelper(context: Context): SQLiteOpenHelper(
    context, "morhealth.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createUsersTable = "CREATE TABLE users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(15) NOT NULL UNIQUE, name VARCHAR(20) NOT NULL, lastname_p VARCHAR(12) NOT NULL, lastname_m VARCHAR(12) NOT NULL, gender INTEGER NOT NULL, age INTEGER NOT NULL, email VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(20) NOT NULL)"
        val createMetricTable = "CREATE TABLE metric (metric_id INTEGER PRIMARY KEY AUTOINCREMENT, metric_name VARCHAR(255) NOT NULL)"
        val createMeasurementsTable = "CREATE TABLE measurements (measurement_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, metric_id INTEGER NOT NULL, value FLOAT NOT NULL, date_time TIMESTAMP NOT NULL, FOREIGN KEY (user_id) REFERENCES users(user_id), FOREIGN KEY (metric_id) REFERENCES metric(metric_id))"
        val createUserIdFK = "CREATE TRIGGER fk_measurement_user_id " +
                "BEFORE INSERT ON measurements " +
                "FOR EACH ROW BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT user_id FROM users WHERE user_id = NEW.user_id) IS NULL) " +
                "THEN RAISE (ABORT, 'Insert on table measurements violates foreign key constraint fk_measurement_user_id') " +
                "END; " +
                "END;"
        val createMetricIdFK = "CREATE TRIGGER fk_measurement_metric_id " +
                "BEFORE INSERT ON measurements " +
                "FOR EACH ROW BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT metric_id FROM metric WHERE metric_id = NEW.metric_id) IS NULL) " +
                "THEN RAISE (ABORT, 'Insert on table measurements violates foreign key constraint fk_measurement_metric_id') " +
                "END; " +
                "END;"

        try {
            db?.apply {
                execSQL(createUsersTable)
                execSQL(createMetricTable)
                execSQL(createMeasurementsTable)
                execSQL(createUserIdFK)
                execSQL(createMetricIdFK)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTable = "DROP TABLE IF EXISTS user"

        try {
            p0?.execSQL(dropTable)
            onCreate(p0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}