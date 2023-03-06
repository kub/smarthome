package com.dirigera.smarthome.orchestration

import com.dirigera.smarthome.common.hub.api.DirigeraClient
import com.dirigera.smarthome.common.hub.dto.Device
import com.dirigera.smarthome.common.hub.dto.DeviceSet
import com.dirigera.smarthome.common.hub.dto.update.LightUpdateFactory
import com.dirigera.smarthome.orchestration.config.Config
import com.dirigera.smarthome.orchestration.config.DeviceConfig
import com.dirigera.smarthome.orchestration.config.DeviceSetConfig
import com.dirigera.smarthome.orchestration.config.RoomConfig
import com.dirigera.smarthome.orchestration.config.ScheduledConfig
import com.dirigera.smarthome.common.utils.HttpClientUtils
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalTime


@Service
open class OrchestrationService(private val dirigeraClient: DirigeraClient, defaultConfig: Config) {
    private val log = KotlinLogging.logger {}

    private var config = defaultConfig
    private lateinit var currentConfig: ScheduledConfig
    private val updatedDevicesIds = mutableSetOf<String>()

    init {
        this.currentConfig = findCurrentConfig()
    }

    @Scheduled(fixedRate = 1000)
    fun runOrchestrationLogic() {
        val newConfig = findCurrentConfig()
        if (newConfig != currentConfig) {
            log.debug("using new configuration $newConfig")
            updatedDevicesIds.clear()
            currentConfig = newConfig
        }

        val devices = HttpClientUtils.handleResponse(dirigeraClient.getDevices().execute())
        currentConfig.roomConfigs.forEach { applyRoomConfig(devices, it) }
    }

    fun setCurrentConfig(config: Config) {
        this.config = config
    }

    fun getCurrentConfig(): Config = config

    fun refreshOrchestrationForControl(controlId: String) {
        updatedDevicesIds.remove(controlId)
    }

    private fun findCurrentConfig(): ScheduledConfig {
        if (config.scheduledConfigs.size == 1) {
            return config.scheduledConfigs[0]
        }

        val localTime = LocalTime.now()
        val sortedScheduledConfigs = config.scheduledConfigs.sortedBy { it.scheduleTime }

        for (idx in sortedScheduledConfigs.indices) {
            if (sortedScheduledConfigs[idx].scheduleTime.isAfter(localTime)) {
                return if (idx > 0) {
                    sortedScheduledConfigs[idx - 1]
                } else {
                    sortedScheduledConfigs.last()
                }
            }
        }

        return sortedScheduledConfigs.last()
    }

    private fun applyRoomConfig(deviceList: List<Device>, roomConfig: RoomConfig) {
        roomConfig.deviceSets.forEach { applyDeviceSetConfig(deviceList, it) }
        roomConfig.devices.forEach { applyDeviceConfig(deviceList, it) }
    }

    private fun applyDeviceSetConfig(devicesList: List<Device>, deviceSetConfig: DeviceSetConfig) {
        val deviceSet = deviceSets(devicesList).find { it.name == deviceSetConfig.name }
        val devices = deviceSetsToDevices(devicesList)[deviceSet]
        devices?.forEach { applyLightSetting(it, deviceSetConfig.lightLevel, deviceSetConfig.colorTemperature) }
    }

    private fun applyDeviceConfig(devicesList: List<Device>, deviceConfig: DeviceConfig) {
        val device = devicesList.find { it.attributes.customName == deviceConfig.name }
        if (device != null) {
            applyLightSetting(device, deviceConfig.lightLevel, deviceConfig.colorTemperature)
        }
    }

    private fun applyLightSetting(device: Device, lightLevel: Int?, colorTemperaturePercent: Int?) {
        log.debug("applying light settings for device ${device.attributes.customName}")
        if (updatedDevicesIds.contains(device.id)) {
            if (!device.isReachable) {
                updatedDevicesIds.remove(device.id)
            }
            log.debug("applying light settings skipped - device ${device.attributes.customName} was already updated")
            return
        }
        if (!device.isReachable) {
            log.debug("applying light settings skipped - device ${device.attributes.customName} is not reachable")
            return
        }

        applyLightSettingInternal(device, lightLevel, colorTemperaturePercent)
        updatedDevicesIds.add(device.id)
        log.debug("light settings lightLevel=$lightLevel and colorTemperaturePercent=$colorTemperaturePercent for device ${device.attributes.customName} were applied")
    }

    private fun applyLightSettingInternal(device: Device, lightLevel: Int?, colorTemperaturePercent: Int?) {
        if (lightLevel != null) {
            HttpClientUtils.handleResponse(dirigeraClient.updateDevice(device.id, LightUpdateFactory.lightLevel(lightLevel)).execute())
        }

        if (colorTemperaturePercent != null) {
            Thread.sleep(500)
            val colorTemperature =
                device.attributes.colorTemperatureMax + (((device.attributes.colorTemperatureMin - device.attributes.colorTemperatureMax) / 100) * 1.coerceAtLeast(
                    colorTemperaturePercent
                ))
            HttpClientUtils.handleResponse(
                dirigeraClient.updateDevice(device.id, LightUpdateFactory.colorTemperature(colorTemperature)).execute()
            )
        }
    }

    private fun deviceSets(devices: List<Device>) = devices.flatMap { it.deviceSet }.distinct()
    private fun deviceSetsToDevices(devices: List<Device>): Map<DeviceSet, List<Device>> =
        devices.filter { it.deviceSet.isNotEmpty() }.groupBy { it.deviceSet[0] }

}

class DirigeraClientException(message: String) : Exception(message)