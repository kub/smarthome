package com.dirigera.smarthome.control.web

import com.dirigera.smarthome.control.service.DirigeraControlService
import com.dirigera.smarthome.control.web.dto.RoomDto
import com.dirigera.smarthome.control.web.dto.UpdateControlDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
open class DirigeraControlController(private val dirigeraControlService: DirigeraControlService) {

    @GetMapping("/api/rooms")
    @CrossOrigin
    fun getRooms(): List<RoomDto> {
        return dirigeraControlService.getRooms()
    }

    @PutMapping("/api/control")
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    fun updateControl(@RequestBody updateControlDto: UpdateControlDto) {
        dirigeraControlService.updateControl(updateControlDto)
    }
}