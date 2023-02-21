package com.example.morhealth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.morhealth.adapters.MeasurementsListAdapter
import com.example.morhealth.data.MeasurementDAO
import com.example.morhealth.databinding.ActivityMetricBinding
import com.example.morhealth.domain.Measurement
import com.example.morhealth.metricfragments.WaterFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*


class MetricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetricBinding

    var metric_id: Int = -1
    var metric_name: String = "Metric"
    private lateinit var metricTag: String

    private lateinit var waterFragment: WaterFragment

    private lateinit var measurements: List<Measurement>
    private lateinit var last7measurements: List<Measurement>

    private var measurementsExpanded: Boolean = false

    private var measurementDAO: MeasurementDAO = MeasurementDAO(this)

    companion object {
        fun Date.isToday(): Boolean {
            val today = Calendar.getInstance().time
            return this.toString().substring(0, 10) == today.toString().substring(0, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMetricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        waterFragment = WaterFragment()

        CoroutineScope(Dispatchers.Main).launch {
            // Realizar la consulta en un hilo en segundo plano
            initMeasurements()
        }

        initMetricProperties()
        initToolbar()

    }

    override fun onStart() {
        super.onStart()

        when (metricTag) {
            "water" -> goWaterFragment()
            else -> Toast.makeText(this, "No hay fragmento", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun initMeasurements() {

        //withContext(Dispatchers.IO) {

            measurements = measurementDAO.selectAllUserMetricMeasurements(LoginActivity.user!!.user_id!!, metric_id)
            last7measurements = measurements.takeLast(7)
            initBarChart()
            initMeasurementsListView()

        //}
    }

    private fun initBarChart() {

        val barChart: BarChart = binding.barChart
        val list: ArrayList<BarEntry> = ArrayList()
        val xAxisLabels: ArrayList<String> = ArrayList()

        last7measurements.forEachIndexed { index, measurement ->
            list.add(BarEntry(index.toFloat(), measurement.value!!.toFloat()))
            val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
            xAxisLabels.add(dateFormat.format(measurement.date_time!!))
        }

        val barDataSet = BarDataSet(list, this.metric_name)

        barDataSet.apply {
            colors = mutableListOf(
                Color.parseColor("#031728"),
                Color.parseColor("#223159"),
                Color.parseColor("#216B91"),
                Color.parseColor("#4B90C0"),
                Color.parseColor("#707173")
            )
            valueTextColor = Color.BLACK
            valueTextSize = 14f
            valueTextColor = getColor(R.color.dark_blue)
        }

        val barData = BarData(barDataSet)

        barChart.apply {
            setFitBars(true)
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            isDoubleTapToZoomEnabled = true
            isDragEnabled = true

            data = barData
            description.text = when (metricTag) {
                "water" -> "agua"
                else -> "Metric"
            }
            animateY(2000)
        }

        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE

        val formatter: ValueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return xAxisLabels[value.toInt()]
            }
        }

        xAxis.granularity = 1f // minimum axis-step (interval) is 1

        xAxis.textColor = Color.WHITE

        xAxis.valueFormatter = formatter


    }

    private fun initMeasurementsListView() {

        binding.rlMeasurements.setOnClickListener {
            val ivExpandMeasurement = binding.ivExpandMeasurements
            val cvMeasurements = binding.cvMeasurements
            val params = cvMeasurements.layoutParams
             if (measurementsExpanded) {
                 measurementsExpanded = false
                 ivExpandMeasurement.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_expand_less))
                 params.height = dpToPx(60, this)
                 cvMeasurements.layoutParams = params
            } else {
                 measurementsExpanded = true
                 ivExpandMeasurement.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_expand))
                 params.height = dpToPx(300, this)
                 cvMeasurements.layoutParams = params
            }
        }

        val measurementsAdapter = MeasurementsListAdapter(measurements, this)
        val lvMeasurementsList = binding.lvMeasurementsList

        lvMeasurementsList.apply {
            adapter = measurementsAdapter
        }

    }

    private fun initToolbar() {
        val toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.title = this.metric_name
    }

    private fun initMetricProperties() {
        try {
            val measurementDAO = MeasurementDAO(this)
            this.metricTag = intent.getStringExtra("metricTag")!!
            this.metric_id = measurementDAO.selectMetricIdByName(metricTag)
            this.metric_name = getString(resources.getIdentifier(metricTag, "string", packageName))
        } catch (e: Exception) {
            e.printStackTrace()
            onBackPressed()
        }
    }

    fun goWaterFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragmentContainer.id, waterFragment)
        transaction.commit()
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}