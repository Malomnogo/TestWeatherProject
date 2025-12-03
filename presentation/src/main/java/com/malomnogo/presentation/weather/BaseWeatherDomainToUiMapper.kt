package com.malomnogo.presentation.weather

import com.malomnogo.domain.ForecastDomain
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain

class BaseWeatherDomainToUiMapper(
    private val temperatureMapper: TemperatureDomain.Mapper<String>
) : WeatherDomain.Mapper<WeatherUiState> {

    override fun mapSuccess(
        city: String,
        temperature: TemperatureDomain,
        forecast: ForecastDomain
    ) = WeatherUiState.Success(
        city = city,
        temperature = temperature.map(temperatureMapper)
    )

    override fun mapError(message: String) = WeatherUiState.Error(message = message)
}