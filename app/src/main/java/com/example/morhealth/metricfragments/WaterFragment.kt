package com.example.morhealth.metricfragments

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import com.example.morhealth.LoginActivity
import com.example.morhealth.MetricActivity
import com.example.morhealth.MetricActivity.Companion.isToday
import com.example.morhealth.R
import com.example.morhealth.adapters.WaterAmountsAdapter
import com.example.morhealth.data.MeasurementDAO
import com.example.morhealth.databinding.FragmentWaterBinding
import com.example.morhealth.domain.Measurement
import com.example.morhealth.domain.WaterAmount

class WaterFragment : Fragment() {

    private lateinit var binding: FragmentWaterBinding

    private lateinit var measurementDAO: MeasurementDAO

    private var todayMeasurement: Measurement? = null

    private val TAG = "WaterFragment"

    private var waterAmounts = mutableListOf(
        WaterAmount(R.drawable.ic_glass_water, "Vaso de agua", 200),
        WaterAmount(R.drawable.ic_bottle_water, "Botella de agua", 1000)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaterBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        measurementDAO = MeasurementDAO(requireContext())
        val measurements = measurementDAO.selectAllUserMetricMeasurements(
            LoginActivity.user!!.user_id!!,
            (activity as MetricActivity).metric_id
        )
        measurements.forEach {
            Log.d("water_measurement", it.toString())
        }

        initWaterAmounts()

        binding.btnAddWaterAmount.setOnClickListener {

            initAlertDialog()

        }
    }

    override fun onResume() {
        super.onResume()

        updateWaterProgress()
    }

    private fun updateWaterProgress() {
        initTodayMeasurement()
        initWaterProgress()
        animateWaterProgress()
        //initWaterDrop()
    }

    private fun initAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_water_amount, null)
        val etLabel: EditText = dialogLayout.findViewById(R.id.etLabel)
        val etAmount: EditText = dialogLayout.findViewById(R.id.etAmount)

        with(builder) {
            setTitle("Ingresa los campos")
            setPositiveButton("OK") {dialog, which ->
                val label = etLabel.text.toString()
                val amount = etAmount.text.toString().toInt()
                addWaterAmount( WaterAmount(label, amount) )
            }
            setNegativeButton("CANCEL") {dialog, which ->
                Log.i(TAG, "AlertDialog canceled")
            }
            setView(dialogLayout)
            show()
        }
    }

    private fun initWaterProgress() {

        if (todayMeasurement != null) {

            val value = todayMeasurement!!.value!!.toInt()

            binding.apply {
                tvDrinkedWater.text = value.toString() + " ml "
                pbDrinkedWater.apply {
                    max = 2000
                    progress = value
                }
            }

        } else {
            binding.apply {
                tvDrinkedWater.text = "0 ml "
                pbDrinkedWater.apply {
                    max = 2000
                    progress = 0
                }
            }
        }
    }

    private fun animateWaterProgress() {
        val progress = if (todayMeasurement != null) {
            todayMeasurement!!.value!!.toInt()
        } else {
            0
        }

        binding.pbDrinkedWater.apply {
            max = 2000
            setProgressWithAnimation(progress)
        }
    }

    private fun ProgressBar.setProgressWithAnimation(to: Int) {
        val animation = ObjectAnimator.ofInt(this, "progress", progress, to)
        animation.duration = 1000
        animation.interpolator = DecelerateInterpolator()
        animation.start()
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

    }

    /*private fun initWaterDrop() {

        val waterDrop = binding.rlWaterDrop
        val params = waterDrop.layoutParams as ViewGroup.MarginLayoutParams

        params.marginStart = dpToPx(-50, requireContext())
        binding.rlWaterDrop.layoutParams = params

        if (binding.pbDrinkedWater.progress == 0) {
            binding.tvDrinkedWater.text = "0"
            return
        }

        val pbDrinkedWater = binding.pbDrinkedWater
        val progress = pbDrinkedWater.progress
        val max = pbDrinkedWater.max

        val targetMargin = (binding.pbDrinkedWaterContainer.width) * progress / max
        val animation = ObjectAnimator.ofFloat(waterDrop, View.TRANSLATION_X, params.marginStart.toFloat(), targetMargin.toFloat())
        animation.duration = 500
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }*/


    private fun initWaterAmounts() {

        val waterAmountsAdapter = WaterAmountsAdapter(waterAmounts, requireContext())
        val lvWaterAmounts = binding.lvWaterAmounts

        lvWaterAmounts.apply {
            adapter = waterAmountsAdapter
            setOnItemClickListener { _, _, position, _ ->
                val waterAmount = waterAmounts[position]
                val waterAmountValue = waterAmount.value!!
                Toast.makeText(requireContext(), "Has tomado $waterAmountValue ml", Toast.LENGTH_SHORT).show()
                drinkWater(waterAmountValue)
            }
        }

    }

    private fun drinkWater(value: Int) {

        if (todayMeasurement == null) {

            val newMeasurement = Measurement(
                null,
                LoginActivity.user!!.user_id!!,
                (activity as MetricActivity).metric_id,
                value.toFloat(),
                null
            )

            val success = measurementDAO.insertMeasurement(newMeasurement)
            Log.i("WaterFragment", "insert success: $success")

        } else {

            val newMeasurement = Measurement(
                todayMeasurement!!.measurement_id,
                todayMeasurement!!.user_id,
                todayMeasurement!!.metric_id,
                todayMeasurement!!.value!! + value,
                todayMeasurement!!.date_time
            )

            measurementDAO.updateMeasurement(newMeasurement)
        }

        updateWaterProgress()

    }

    private fun addWaterAmount(waterAmount: WaterAmount) {
        val waterAmountsAdapter = binding.lvWaterAmounts.adapter as WaterAmountsAdapter
        waterAmountsAdapter.addNewWaterAmount(waterAmount)
    }

   /* private fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }*/

}