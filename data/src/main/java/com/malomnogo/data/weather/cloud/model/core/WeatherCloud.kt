package com.malomnogo.data.weather.cloud.model.core

import com.google.gson.annotations.SerializedName
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloud

data class WeatherCloud(
    @SerializedName("location")
    private val location: LocationCloud?,
    @SerializedName("current")
    private val current: TemperatureCloud?
) {
    fun <T : Any> map(mapper: WeatherCloudRemoteMapper<T>) = mapper.map(
        location = location,
        current = current
    )
}

