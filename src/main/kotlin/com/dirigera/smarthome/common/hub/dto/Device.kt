package com.dirigera.smarthome.common.hub.dto

import com.fasterxml.jackson.annotation.JsonValue
import java.time.Instant

data class Device(
    val id: String,
    val type: DeviceType,
    val isReachable: Boolean,
    val lastSeen: Instant,
    val attributes: Attributes,
    val room: Room?,
    val deviceSet: List<DeviceSet>

)

data class Attributes(
    val customName: String,
    val isOn: Boolean,
    val lightLevel: Int,
    val colorTemperature: Int,
    val colorTemperatureMin: Int,
    val colorTemperatureMax: Int,
)


data class DeviceSet(
    val id: String,
    val name: String
)

enum class DeviceType(private val type: String) {
    GATEWAY("gateway"),
    LIGHT("light"),
    OUTLET("outlet"),
    SENSOR("sensor"),
    CONTROLLER("controller");


    @JsonValue
    open fun toValue(): String {
        return this.type
    }
}