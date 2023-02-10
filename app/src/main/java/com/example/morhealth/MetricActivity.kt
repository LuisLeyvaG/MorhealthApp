package com.example.morhealth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.morhealth.data.MeasurementDAO
import com.example.morhealth.databinding.ActivityMetricBinding


class MetricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMetricBinding

    private var metric_id: Int = -1
    private var metric_name: String = "Metric"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMetricBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMetricProperties()
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.title = this.metric_name
    }

    private fun initMetricProperties() {
        val metricTag = intent.getStringExtra("metricTag")!!
        val measurementDAO = MeasurementDAO(this)
        this.metric_id = measurementDAO.selectMetricIdByName(metric_name)
        this.metric_name = getString(resources.getIdentifier(metricTag, "string", packageName))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}