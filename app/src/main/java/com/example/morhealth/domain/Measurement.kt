package com.example.morhealth.domain

import java.util.Date

class Measurement {
    var user_id: Int? = null
    var metric_id: Int? = null
    var value: Float? = null
    var date_time: Date? = null

    constructor() {}

    constructor(user_id: Int?, metric_id: Int?, value: Float?) {
        this.user_id = user_id
        this.metric_id = metric_id
        this.value = value
    }

    constructor(value: Float?, date_time: Date?) {
        this.value = value
        this.date_time = date_time
    }

}