package com.example.morhealth.domain

import java.util.Date

class Measurement {
    var measurement_id: Int? = null
    var user_id: Int? = null
    var metric_id: Int? = null
    var value: Float? = null
    var date_time: Date? = null

    constructor() {}

    constructor(measurement_id: Int?, user_id: Int?, metric_id: Int?, value: Float?, date_time: Date?) {
        this.measurement_id = measurement_id
        this.user_id = user_id
        this.metric_id = metric_id
        this.value = value
        this.date_time = date_time
    }

    constructor(measurement_id: Int?, value: Float?) {
        this.measurement_id = measurement_id
        this.value = value
    }

    constructor(measurement_id: Int?, value: Float?, date_time: Date?) {
        this.measurement_id = measurement_id
        this.value = value
        this.date_time = date_time
    }

    constructor(user_id: Int?, metric_id: Int?, value: Float?) {
        this.user_id = user_id
        this.metric_id = metric_id
        this.value = value
    }

    constructor(value: Float?, date_time: Date?) {
        this.value = value
        this.date_time = date_time
    }

    override fun toString(): String {
        return "Measurement(measurement_id=$measurement_id, user_id=$user_id, metric_id=$metric_id, value=$value, date_time=$date_time)"
    }


}