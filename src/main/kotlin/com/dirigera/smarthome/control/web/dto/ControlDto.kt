package com.dirigera.smarthome.control.web.dto

import java.time.Instant

data class ControlDto(
    val id: String,
    val type: ControlType,
    val isOn: Boolean,
    val name: String,
    val lightLevel: Int,
    val colorTemperature: Int,
    val lastSeen: Instant,
    val isReachable: Boolean
)

enum class ControlType {
    DEVICE,
    DEVICE_SET
}
