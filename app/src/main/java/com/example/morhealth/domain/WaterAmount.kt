package com.example.morhealth.domain

import com.example.morhealth.R

class WaterAmount {
    var icon: Int = R.drawable.ic_water_drop
    var label: String? = null
    var value: Int? = null

    constructor() {}

    constructor(icon: Int, label: String?, value: Int?) {
        this.icon = icon
        this.label = label
        this.value = value
    }

    constructor(label: String?, value: Int?) {
        this.label = label
        this.value = value
    }
}