package com.dirigera.smarthome.common.hub.api

import com.dirigera.smarthome.common.hub.dto.Device
import com.dirigera.smarthome.common.hub.dto.Room
import com.dirigera.smarthome.common.hub.dto.update.DeviceUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path


interface DirigeraClient {

    @GET("/v1/devices")
    fun getDevices(): Call<List<Device>>

    @GET("/v1/rooms")
    fun getRooms(): Call<List<Room>>

    @PATCH("/v1/devices/{deviceId}")
    fun updateDevice(@Path("deviceId") deviceId: String, @Body lightOnOff: List<DeviceUpdate>): Call<Void>
}