package com.example.morhealth.metricfragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.morhealth.LoginActivity
import com.example.morhealth.MetricActivity
import com.example.morhealth.MetricActivity.Companion.isToday
import com.example.morhealth.data.MeasurementDAO
import com.example.morhealth.databinding.FragmentStepsBinding
import com.example.morhealth.domain.Measurement
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.request.OnDataPointListener
import java.util.*
class StepsFragment : Fragment(), SensorEventListener {

    private lateinit var binding: FragmentStepsBinding

    private lateinit var measurementDAO: MeasurementDAO

    private var sensorManager: SensorManager? = null

    private var todayMeasurement: Measurement? = null

    private var running = false
    private var totalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        measurementDAO = MeasurementDAO(requireContext())
        updateStepsProgress()

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed({
            running = true
            val stepSensor: Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if (stepSensor == null) {
                Toast.makeText(requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show()
            } else {
                sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            }
        }, 1000)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (running) {
            totalSteps = p0!!.values[0]
            resetSteps()
        }
    }

    private fun resetSteps() {
        binding.apply {
            tvSteps.text = totalSteps.toInt().toString()
            progressCircular.apply {
                setProgressWithAnimation(totalSteps)
            }
        }
    }

    override fun onDestroyView() {
        saveData()
        super.onDestroyView()
    }

    private fun saveData() {

        if (todayMeasurement == null) {

            val newMeasurement = Measurement(
                null,
                LoginActivity.user!!.user_id!!,
                (activity as MetricActivity).metric_id,
                totalSteps.toFloat(),
                null
            )

            val success = measurementDAO.insertMeasurement(newMeasurement)
            Log.i("StepsFragment", "insert success: $success")

        } else {

            val newMeasurement = Measurement(
                todayMeasurement!!.measurement_id,
                todayMeasurement!!.user_id,
                todayMeasurement!!.metric_id,
                todayMeasurement!!.value!!,
                todayMeasurement!!.date_time
            )

            measurementDAO.updateMeasurement(newMeasurement)
        }

    }

    private fun updateStepsProgress() {
        initTodayMeasurement()
        resetSteps()
        //initWaterDrop()
    }

    private fun initTodayMeasurement() {

        val lastMeasurementList = measurementDAO.selectLastUserMetricMeasurements(
            LoginActivity.user!!.user_id!!,
            (activity as MetricActivity).metric_id
        )

        todayMeasurement = if (lastMeasurementList.isNotEmpty()) {
            val lastMeasurement = lastMeasurementList[0]
            if (lastMeasurement.date_time!!.isToday()) {
                lastMeasurement
            } else {
                null
            }
        } else {
            null
        }


        if (todayMeasurement != null) {
            totalSteps = todayMeasurement!!.value!!
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}