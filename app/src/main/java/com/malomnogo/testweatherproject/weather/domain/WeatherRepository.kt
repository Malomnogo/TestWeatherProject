package com.malomnogo.testweatherproject.weather.domain

interface WeatherRepository {

    suspend fun loadData(): WeatherDomain
}