package com.dirigera.smarthome

import com.dirigera.smarthome.common.hub.cert.HttpsTrustManager
import com.dirigera.smarthome.orchestration.config.Config
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver


@EnableScheduling
@SpringBootApplication
class SmarthomeApplication(val objectMapper: ObjectMapper) : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(
                object : PathResourceResolver() {
                    override fun getResource(resourcePath: String, location: Resource): Resource {
                        val requestedResource: Resource = location.createRelative(resourcePath)
                        return if (requestedResource.exists() && requestedResource.isReadable) requestedResource else ClassPathResource(
                            "/static/index.html"
                        )
                    }
                })
    }


    @Bean
    open fun config(): Config {
        return objectMapper.readValue(defaultConfig(), Config::class.java)
    }

    fun defaultConfig(): String = """
        {
          "scheduledConfigs": [
            {
              "scheduleTime": [
                23,
                0
              ],
              "roomConfigs": [
                {
                  "name": "Living room",
                  "deviceSets": [
                    {
                      "name": "Sofa",
                      "lightLevel": 25,
                      "colorTemperature": 1
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Kid room",
                  "deviceSets": [
                    {
                      "name": "Kid light",
                      "lightLevel": 1,
                      "colorTemperature": 1
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Bathroom",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "Bathroom",
                      "lightLevel": 45,
                      "colorTemperature": 10
                    }
                  ]
                }
              ]
            },
            {
              "scheduleTime": [
                18,
                0
              ],
              "roomConfigs": [
                {
                  "name": "Bedroom",
                  "deviceSets": [
                    {
                      "name": "Wardrobe lights",
                      "lightLevel": 35,
                      "colorTemperature": 10
                    }
                  ],
                  "devices": [
                    {
                      "name": "Bed",
                      "lightLevel": 35,
                      "colorTemperature": 10,
                      "colorHue": 29.997378975826734,
                      "colorSaturation": 0.4457221253144956
                    }
                  ]
                },
                {
                  "name": "Toilet",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "Toilet",
                      "lightLevel": 45,
                      "colorTemperature": 10
                    }
                  ]
                },
                {
                  "name": "Office",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "office light",
                      "lightLevel": 60
                    }
                  ]
                },
                {
                  "name": "Kid room",
                  "deviceSets": [
                    {
                      "name": "Kid light",
                      "lightLevel": 45,
                      "colorTemperature": 10
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Hallway",
                  "deviceSets": [
                    {
                      "name": "Hallway",
                      "lightLevel": 45,
                      "colorTemperature": 20
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Living room",
                  "deviceSets": [
                    {
                      "name": "Table",
                      "lightLevel": 50,
                      "colorTemperature": 10
                    },
                    {
                      "name": "Kitchen",
                      "lightLevel": 90,
                      "colorTemperature": 50
                    },
                    {
                      "name": "Sofa",
                      "lightLevel": 50,
                      "colorTemperature": 10
                    }
                  ],
                  "devices": []
                }
              ]
            },
            {
              "scheduleTime": [
                7,
                30
              ],
              "roomConfigs": [
                {
                  "name": "Bedroom",
                  "deviceSets": [
                    {
                      "name": "Wardrobe lights",
                      "lightLevel": 100,
                      "colorTemperature": 100
                    }
                  ],
                  "devices": [
                    {
                      "name": "Bed",
                      "lightLevel": 80,
                      "colorTemperature": 75
                    }
                  ]
                },
                {
                  "name": "Bathroom",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "Bathroom",
                      "lightLevel": 100,
                      "colorTemperature": 100
                    }
                  ]
                },
                {
                  "name": "Toilet",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "Toilet",
                      "lightLevel": 100,
                      "colorTemperature": 100
                    }
                  ]
                },
                {
                  "name": "Office",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "office light",
                      "lightLevel": 90
                    }
                  ]
                },
                {
                  "name": "Kid room",
                  "deviceSets": [
                    {
                      "name": "Kid light",
                      "lightLevel": 80,
                      "colorTemperature": 75
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Hallway",
                  "deviceSets": [
                    {
                      "name": "Hallway",
                      "lightLevel": 100,
                      "colorTemperature": 75
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Living room",
                  "deviceSets": [
                    {
                      "name": "Table",
                      "lightLevel": 80,
                      "colorTemperature": 75
                    },
                    {
                      "name": "Kitchen",
                      "lightLevel": 100,
                      "colorTemperature": 75
                    },
                    {
                      "name": "Sofa",
                      "lightLevel": 80,
                      "colorTemperature": 75
                    }
                  ],
                  "devices": []
                }
              ]
            },
            {
              "scheduleTime": [
                6,
                30
              ],
              "roomConfigs": [
                {
                  "name": "Bedroom",
                  "deviceSets": [
                    {
                      "name": "Wardrobe lights",
                      "lightLevel": 80,
                      "colorTemperature": 65
                    }
                  ],
                  "devices": [
                    {
                      "name": "Bed",
                      "lightLevel": 50,
                      "colorTemperature": 75
                    }
                  ]
                },
                {
                  "name": "Bathroom",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "Bathroom",
                      "lightLevel": 80,
                      "colorTemperature": 65
                    }
                  ]
                },
                {
                  "name": "Toilet",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "Toilet",
                      "lightLevel": 80,
                      "colorTemperature": 65
                    }
                  ]
                },
                {
                  "name": "Office",
                  "deviceSets": [],
                  "devices": [
                    {
                      "name": "office light",
                      "lightLevel": 90
                    }
                  ]
                },
                {
                  "name": "Kid room",
                  "deviceSets": [
                    {
                      "name": "Kid light",
                      "lightLevel": 80,
                      "colorTemperature": 70
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Hallway",
                  "deviceSets": [
                    {
                      "name": "Hallway",
                      "lightLevel": 60,
                      "colorTemperature": 50
                    }
                  ],
                  "devices": []
                },
                {
                  "name": "Living room",
                  "deviceSets": [
                    {
                      "name": "Table",
                      "lightLevel": 80,
                      "colorTemperature": 50
                    },
                    {
                      "name": "Kitchen",
                      "lightLevel": 90,
                      "colorTemperature": 50
                    },
                    {
                      "name": "Sofa",
                      "lightLevel": 80,
                      "colorTemperature": 50
                    }
                  ],
                  "devices": []
                }
              ]
            }
          ]
        }
    """.trimIndent()
}

fun main(args: Array<String>) {
    HttpsTrustManager.allowAllSSL()

    runApplication<SmarthomeApplication>(*args)
}


