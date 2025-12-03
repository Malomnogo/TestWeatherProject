package com.malomnogo.data.weather.cloud.model.forecastDay

import com.google.gson.annotations.SerializedName
import com.malomnogo.data.weather.cloud.model.forecastDay.day.DayCloud
import com.malomnogo.data.weather.cloud.model.forecastDay.hour.HourCloud

data class ForecastDayCloud(
    @SerializedName("date_epoch")
    private val dateEpoch: Long?,
    @SerializedName("day")
    private val day: DayCloud?,
    @SerializedName("hour")
    private val hour: List<HourCloud>?
) {
    fun <T : Any> map(mapper: ForecastDayCloudMapper<T>) = mapper.map(
        dateEpoch = dateEpoch,
        day = day,
        hour = hour
    )
}