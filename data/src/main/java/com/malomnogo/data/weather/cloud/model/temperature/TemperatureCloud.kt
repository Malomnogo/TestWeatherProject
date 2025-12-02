package com.malomnogo.data.weather.cloud.model.temperature

import com.google.gson.annotations.SerializedName

data class TemperatureCloud(
    @SerializedName("temp_c")
    private val tempC: Double?
) {

    fun <T : Any> map(mapper: TemperatureCloudRemoteMapper<T>) = mapper.map(temperature = tempC)
}