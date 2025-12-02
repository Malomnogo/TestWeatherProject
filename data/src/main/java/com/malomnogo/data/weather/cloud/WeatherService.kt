package com.malomnogo.data.weather.cloud

import com.malomnogo.data.weather.cloud.model.core.WeatherCloud
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast.json")
    fun fetchForecast(
        @Query("key") key: String,
        @Query("q") coordinates: String,
        @Query("days") days: Int
    ): Call<WeatherCloud>
}