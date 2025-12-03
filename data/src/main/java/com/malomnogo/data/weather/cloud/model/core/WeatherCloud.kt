package com.malomnogo.data.weather.cloud.model.core

import com.google.gson.annotations.SerializedName
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloud
import com.malomnogo.data.weather.cloud.model.forecast.ForecastCloud
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.temperature.CurrentTemperatureCloud
import com.malomnogo.domain.ConditionDomain

data class WeatherCloud(
    @SerializedName("location")
    private val location: LocationCloud?,
    @SerializedName("current")
    private val current: CurrentTemperatureCloud?,
    @SerializedName("forecast")
    private val forecast: ForecastCloud?
) {
    fun <T : Any> map(mapper: WeatherCloudMapper<T>) = mapper.map(
        location = location,
        current = current,
        forecast = forecast
    )
}