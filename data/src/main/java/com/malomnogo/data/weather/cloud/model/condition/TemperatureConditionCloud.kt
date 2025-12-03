package com.malomnogo.data.weather.cloud.model.condition

import com.google.gson.annotations.SerializedName

data class TemperatureConditionCloud(
    @SerializedName("text")
    private val text: String?,
    @SerializedName("icon")
    private val icon: String?
) {
    fun <T : Any> map(mapper: TemperatureConditionCloudMapper<T>) = mapper.map(
        text = text,
        icon = icon
    )
}