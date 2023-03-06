package com.dirigera.smarthome.common.hub.dto.update

import com.dirigera.smarthome.control.web.dto.UpdateControlDto


class LightUpdateFactory private constructor() {
    companion object {
        fun on() = listOf(DeviceUpdate(attributes = LightAttributes(isOn = true)))
        fun off() = listOf(DeviceUpdate(attributes = LightAttributes(isOn = false)))
        fun lightLevel(value: Int) = listOf(DeviceUpdate(attributes = LightAttributes(lightLevel = value)))
        fun colorTemperature(value: Int) = listOf(DeviceUpdate(attributes = LightAttributes(colorTemperature = value)))
    }
}
