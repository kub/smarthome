package com.dirigera.smarthome.orchestration.config

import java.time.LocalTime

data class ScheduledConfig(val scheduleTime: LocalTime, val roomConfigs: List<RoomConfig>)
