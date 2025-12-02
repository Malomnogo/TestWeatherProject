package com.malomnogo.data.weather.cloud.model.temperature

import com.google.gson.annotations.SerializedName
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloud

data class CurrentTemperatureCloud(
    @SerializedName("temp_c")
    private val tempC: Double?,
    @SerializedName("condition")
    private val condition: TemperatureConditionCloud?
) {

    fun <T : Any> map(mapper: TemperatureCloudMapper<T>) = mapper.map(
        temperature = tempC,
        condition = condition
    )
}