package com.dirigera.smarthome.orchestration.web

import com.dirigera.smarthome.orchestration.OrchestrationService
import com.dirigera.smarthome.orchestration.config.Config
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
open class OrchestrationController(val orchestrationService: OrchestrationService) {

    @GetMapping("/api/config")
    fun getCurrentConfig(): Config {
        return orchestrationService.getCurrentConfig()
    }

    @PutMapping("/api/config")
    fun setCurrentConfig(@RequestBody config: Config) {
        orchestrationService.setCurrentConfig(config)
    }
}