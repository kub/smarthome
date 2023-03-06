package com.dirigera.smarthome.common.hub.dto.update

data class LightAttributes(
    override val isOn: Boolean? = null,
    val lightLevel: Int? = null,
    val colorTemperature: Int? = null,
) : BaseDeviceUpdateAttributes()