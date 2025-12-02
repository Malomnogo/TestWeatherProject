package com.malomnogo.data.weather.cloud.model.forecastDay.hour

import com.google.gson.annotations.SerializedName

data class HourCloud(
    @SerializedName("time_epoch")
    private val timeEpoch: Long?,
    @SerializedName("temp_c")
    private val tempC: Double?
) {
    fun <T : Any> map(mapper: HourCloudMapper<T>) = mapper.map(timeEpoch = timeEpoch, tempC = tempC)
}

