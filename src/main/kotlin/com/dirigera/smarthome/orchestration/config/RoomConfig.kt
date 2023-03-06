package com.dirigera.smarthome.orchestration.config

data class RoomConfig(val name: String, val deviceSets: List<DeviceSetConfig>, val devices: List<DeviceConfig>)

data class DeviceSetConfig(val name: String, val lightLevel: Int?, val colorTemperature: Int?)

data class DeviceConfig(val name: String, val lightLevel: Int?, val colorTemperature: Int?)


