package com.dirigera.smarthome.control.web.dto

data class UpdateControlDto(
    val id: String,
    val isOn: Boolean?,
    val lightLevel: Int?,
    val colorTemperature: Int?,
    val type: ControlType
)
