package com.malomnogo.data.weather.cloud.model.forecastDay.day

import com.google.gson.annotations.SerializedName
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloud

data class DayCloud(
    @SerializedName("maxtemp_c")
    private val maxTempC: Double?,
    @SerializedName("mintemp_c")
    private val minTempC: Double?,
    @SerializedName("condition")
    private val conditionDomain: TemperatureConditionCloud?
) {
    fun <T : Any> map(mapper: DayCloudMapper<T>) = mapper.map(
        maxTempC = maxTempC,
        minTempC = maxTempC,
        conditionDomain = conditionDomain
    )
}

