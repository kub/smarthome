package com.dirigera.smarthome.control.service

import com.dirigera.smarthome.control.web.dto.ControlDto
import com.dirigera.smarthome.control.web.dto.ControlType
import com.dirigera.smarthome.control.web.dto.RoomDto
import com.dirigera.smarthome.control.web.dto.UpdateControlDto
import com.dirigera.smarthome.common.hub.api.DirigeraClient
import com.dirigera.smarthome.common.hub.dto.Device
import com.dirigera.smarthome.common.hub.dto.DeviceType
import com.dirigera.smarthome.common.hub.dto.update.LightUpdateFactory
import com.dirigera.smarthome.orchestration.OrchestrationService
import com.dirigera.smarthome.orchestration.config.ScheduledConfig
import com.dirigera.smarthome.common.utils.HttpClientUtils
import org.springframework.stereotype.Service

@Service
class DirigeraControlService(
    private val dirigeraClient: DirigeraClient,
    private val orchestrationService: OrchestrationService) {

    fun getRooms(): List<RoomDto> {
        val devices = getLights()
        val rooms = HttpClientUtils.handleResponse(dirigeraClient.getRooms().execute())

        return rooms.map { room ->
            val roomDevices = devices
                .filter { it.room?.id == room.id && it.deviceSet.isEmpty() }
                .map {
                    createControl(it.id, it.attributes.customName, it, ControlType.DEVICE)
                }

            val deviceSets =
                devices.filter { it.room?.id == room.id && it.deviceSet.isNotEmpty() }.flatMap { it.deviceSet }
                    .toSet()

            val roomDeviceSets = deviceSets.map { ds ->
                devices.first { it.deviceSet.contains(ds) }.let {
                    createControl(ds.id, ds.name, it, ControlType.DEVICE_SET)
                }
            }
            RoomDto(room.id, room.name, roomDevices + roomDeviceSets)
        }
    }

    fun updateControl(updateControlDto: UpdateControlDto) {
        val controls =
            if (updateControlDto.type == ControlType.DEVICE) {
                listOf(checkNotNull(getLights().find { it.id == updateControlDto.id }))
            } else {
                getLights().filter { light -> light.deviceSet.find { it.id == updateControlDto.id } != null }
            }

        val onOffUpdate = if (updateControlDto.isOn == true) LightUpdateFactory.on() else LightUpdateFactory.off()
        for (control in controls) {
            if (updateControlDto.isOn == true && !control.attributes.isOn) {
                refreshOrchestrationForControl(control.id)
            }
            HttpClientUtils.handleResponse(dirigeraClient.updateDevice(control.id, onOffUpdate).execute())
        }
        if (updateControlDto.lightLevel != null) {
            val lightLevelUpdate = LightUpdateFactory.lightLevel(updateControlDto.lightLevel)
            for (control in controls) {
                HttpClientUtils.handleResponse(dirigeraClient.updateDevice(control.id, lightLevelUpdate).execute())
            }
        }
    }

    private fun refreshOrchestrationForControl(controlId: String) {
        this.orchestrationService.refreshOrchestrationForControl(controlId)
    }

    private fun getLights() =
        HttpClientUtils.handleResponse(dirigeraClient.getDevices().execute()).filter { it.type == DeviceType.LIGHT }


    private fun createControl(id: String, name: String, device: Device, controlType: ControlType): ControlDto =
        ControlDto(
            id,
            controlType,
            device.attributes.isOn,
            name,
            device.attributes.lightLevel,
            device.attributes.colorTemperature,
            device.lastSeen,
            device.isReachable
        )

}