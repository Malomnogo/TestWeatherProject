package com.malomnogo.data.weather.cloud.model.forecast

import com.google.gson.annotations.SerializedName
import com.malomnogo.data.weather.cloud.model.forecastDay.ForecastDayCloud

data class ForecastCloud(
    @SerializedName("forecastday")
    private val forecastDay: List<ForecastDayCloud>?
) {
    fun <T : Any> map(mapper: ForecastCloudMapper<T>) = mapper.map(forecastDay = forecastDay)
}



