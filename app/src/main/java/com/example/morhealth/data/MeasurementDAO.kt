package com.example.morhealth.data

import android.content.ContentValues
import android.content.Context
import com.example.morhealth.domain.Measurement
import java.text.SimpleDateFormat
import java.util.*

class MeasurementDAO(context: Context) : mySQLiteHelper(context) {

    private val SELECT_USER_METRIC_MEASUREMENTS = "SELECT value, date_time FROM measurements WHERE metric_id = ? AND user_id = ? ORDER BY date_time;"
    private val SELECT_LAST_USER_METRIC_MEASUREMENTS = "SELECT value, date_time FROM measurements WHERE metric_id = ? AND user_id = ? ORDER BY date_time DESC LIMIT ?;"
    private val SELECT_METRIC_ID_BY_NAME = "SELECT metric_id FROM metrics WHERE metric_name = ?"

    fun insertMeasurement(measurement: Measurement): Boolean {

        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateTime = dateFormat.format(currentDateTime)

        val data = ContentValues().apply {
            put("user_id", measurement.user_id)
            put("metric_id", measurement.metric_id)
            put("value", measurement.value)
            put("date_time", dateTime)
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


    fun selectAllUserMetricMeasurements(user_id: Int, metric_id: Int): List<Measurement> {

        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_USER_METRIC_MEASUREMENTS, arrayOf(metric_id.toString(), user_id.toString()))

        return try {
            cursor.use {
                val measurements = mutableListOf<Measurement>()
                if (it.moveToFirst()) {
                    do {
                        measurements.add(Measurement(it.getFloat(0), Date(it.getString(1))))
                    } while (it.moveToNext())
                }
                measurements
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            db.close()
        }

    }

    fun selectLastUserMetricMeasurements(user_id: Int, metric_id: Int, limit: Int): List<Measurement> {

        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_LAST_USER_METRIC_MEASUREMENTS, arrayOf(metric_id.toString(), user_id.toString(), limit.toString()))

        return try {
            cursor.use {
                val measurements = mutableListOf<Measurement>()
                if (it.moveToFirst()) {
                    do {
                        measurements.add(Measurement(it.getFloat(0), Date(it.getString(1))))
                    } while (it.moveToNext())
                }
                measurements.reversed()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            db.close()
        }

    }

    fun selectMetricIdByName(metric_name: String): Int {

        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_METRIC_ID_BY_NAME, arrayOf(metric_name))

        return try {
            cursor.use {
                if (it.moveToFirst()) {
                    it.getInt(0)
                } else {
                    -1
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {
            db.close()
        }

    }

}