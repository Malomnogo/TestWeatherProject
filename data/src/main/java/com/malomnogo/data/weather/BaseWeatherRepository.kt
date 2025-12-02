package com.malomnogo.data

import com.malomnogo.domain.WeatherDomain
import com.malomnogo.domain.WeatherRepository

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