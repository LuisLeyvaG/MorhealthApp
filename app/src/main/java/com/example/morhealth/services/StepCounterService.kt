package com.example.morhealth.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.Toast

class StepCounterService : Service(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null
    private var previousTotalSteps: Float = 0f
    private var lastStepDetectedTime: Long = 0L
    private var lastStepsRecorded: Int = 0
    private var isSensorRegistered: Boolean = false
    private var totalSteps: Int = 0 // almacenar el total de pasos
    private val STEPS_THRESHOLD = 50 // umbral de pasos para actualizar la base de datos

    companion object {
        private const val TAG = "StepCounterService"
        private const val INACTIVITY_TIME_THRESHOLD = 60 * 1000 // 1 minute
        private const val SAVING_POWER_SAMPLING_PERIOD = 5 * 60 * 1000 // 5 minutes
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Step counter service created.")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startStepCounter()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopStepCounter()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val currentSteps = event.values[0] - previousTotalSteps
            if (currentSteps.toInt() > 0) {
                previousTotalSteps = event.values[0]
                lastStepDetectedTime = System.currentTimeMillis()
                totalSteps += currentSteps.toInt() // actualiza el total de pasos
                checkStepsThreshold() // verifica si se ha alcanzado el umbral de pasos
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun startStepCounter() {
        if (stepSensor == null) {
            Log.e(TAG, "Step counter sensor not available.")
            return
        }
        if (isSensorRegistered) {
            Log.w(TAG, "Step counter sensor already registered.")
            return
        }
        val samplingPeriodUs = if (isInSavingPowerMode()) {
            SAVING_POWER_SAMPLING_PERIOD * 1000 // 5 minutes in microseconds
        } else {
            SensorManager.SENSOR_DELAY_NORMAL
        }
        sensorManager?.registerListener(this, stepSensor, samplingPeriodUs)
        isSensorRegistered = true
    }

    private fun stopStepCounter() {
        if (!isSensorRegistered) {
            Log.w(TAG, "Step counter sensor not registered.")
            return
        }
        sensorManager?.unregisterListener(this)
        isSensorRegistered = false
    }

    private fun updateDatabase(steps: Int) {
        // Actualiza el registro de los pasos de hoy en la base de datos
        Toast.makeText(this, "Total steps: $steps", Toast.LENGTH_SHORT).show()
    }

    private fun isInSavingPowerMode(): Boolean {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isPowerSaveMode
    }

    private fun isDeviceInactive(): Boolean {
        val elapsedTime = System.currentTimeMillis() - lastStepDetectedTime
        return elapsedTime >= INACTIVITY_TIME_THRESHOLD
    }

    // Implementa la lógica para actualizar la base de datos solo cuando se alcanza el umbral de pasos
    private fun checkStepsThreshold() {
        if (totalSteps - lastStepsRecorded >= STEPS_THRESHOLD) {
            updateDatabase(totalSteps)
            lastStepsRecorded = totalSteps
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        // Reinicia el servicio cuando se elimina la tarea de la aplicación
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }
}
