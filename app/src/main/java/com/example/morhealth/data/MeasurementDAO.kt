package com.example.morhealth.data

import android.content.ContentValues
import android.content.Context
import com.example.morhealth.domain.Measurement
import java.util.Date

class MeasurementDAO(context: Context) : mySQLiteHelper(context) {

    private val SELECT_USER_METRIC_MEASUREMENTS = "SELECT value, date_time FROM measurements WHERE metric_id = ? AND user_id = ? ORDER BY date_time;"

    fun insertMeasurement(measurement: Measurement): Boolean {

        val data = ContentValues().apply {
            put("user_id", measurement.user_id)
            put("metric_id", measurement.metric_id)
            put("value", measurement.value)
            put("date_time", measurement.date_time)
        }

        val db = this.writableDatabase
        return try {
            db.insert("measurements", null, data) != -1L
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }

    }

    fun selectUserMetricMeasurements(user_id: Int, metric_id: Int): Map<Date, Float> {

        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_USER_METRIC_MEASUREMENTS, arrayOf(metric_id.toString(), user_id.toString()))

        return try {
            cursor.use {
                val measurements = mutableMapOf<Date, Float>()
                if (it.moveToFirst()) {
                    do {
                        measurements[Date(it.getString(1))] = it.getFloat(0)
                    } while (it.moveToNext())
                }
                measurements
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyMap()
        } finally {
            db.close()
        }

    }




}