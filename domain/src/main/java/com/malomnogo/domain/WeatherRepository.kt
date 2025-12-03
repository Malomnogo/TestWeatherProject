package com.malomnogo.domain

interface WeatherRepository {

    suspend fun loadData(): WeatherDomain
}