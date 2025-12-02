package com.malomnogo.testweatherproject.weather.data

import com.malomnogo.testweatherproject.weather.domain.WeatherDomain
import com.malomnogo.testweatherproject.weather.domain.WeatherRepository
import kotlinx.coroutines.delay

class MockWeatherRepository : WeatherRepository {

    private var attempts: Int = 0

    override suspend fun loadData(): WeatherDomain {
        attempts++
        return if (attempts < 2) {
            WeatherDomain.Error(message = "No internet connection")
        } else {
            WeatherDomain.Success(
                city = "Moscow",
                temperature = 30.0
            )
        }
    }
}