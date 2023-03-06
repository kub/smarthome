package com.dirigera.smarthome.control.web.dto

data class RoomDto(
    val id: String,
    val name: String,
    val controls: List<ControlDto>
)