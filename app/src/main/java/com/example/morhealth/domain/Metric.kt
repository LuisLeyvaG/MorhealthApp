package com.example.morhealth.domain

class Metric {
    var metric_id: Int? = null
    var name: String? = null

    constructor() {}

    constructor(metric_id: Int?, name: String?) {
        this.metric_id = metric_id
        this.name = name
    }
}