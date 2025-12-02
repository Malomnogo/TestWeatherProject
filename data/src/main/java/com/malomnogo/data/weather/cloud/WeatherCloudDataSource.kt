package com.malomnogo.data.weather.cloud

import com.malomnogo.data.core.ProvideApiKey
import com.malomnogo.data.weather.cloud.model.core.WeatherCloud
import retrofit2.Retrofit

interface WeatherCloudDataSource {

    suspend fun fetchForecast(
        coordinates: String,
        days: Int
    ): WeatherCloud

    class Base(
        private val service: WeatherService,
        private val provideApiKey: ProvideApiKey
    ) : WeatherCloudDataSource {

        constructor(retrofit: Retrofit, provideApiKey: ProvideApiKey) : this(
            service = retrofit.create(WeatherService::class.java),
            provideApiKey = provideApiKey
        )

        override suspend fun fetchForecast(
            coordinates: String,
            days: Int
        ) = service.fetchForecast(
            key = provideApiKey.weatherApiKey(),
            coordinates = coordinates,
            days = days
        ).execute().body()!!
    }
}