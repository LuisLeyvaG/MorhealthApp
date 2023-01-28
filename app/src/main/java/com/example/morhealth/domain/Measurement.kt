package com.example.morhealth.domain

class Measurement {
    var measurement_id: Int? = null
    var user_id: Int? = null
    var metric: Metric? = null
    var value: Float? = null
    var date_time: String? = null

    constructor() {}

    constructor(measurement_id: Int?, user_id: Int?, metric: Metric?, value: Float?, date_time: String?) {
        this.measurement_id = measurement_id
        this.user_id = user_id
        this.metric = metric
        this.value = value
        this.date_time = date_time
    }

}