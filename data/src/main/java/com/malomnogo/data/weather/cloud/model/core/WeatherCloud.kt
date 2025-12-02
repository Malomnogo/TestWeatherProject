package com.malomnogo.data.weather.cloud

import com.google.gson.annotations.SerializedName

data class WeatherCloud(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Current
) {
    data class Location(
        @SerializedName("name")
        val name: String
    )

    data class Current(
        @SerializedName("temp_c")
        val tempC: Double
    )
}