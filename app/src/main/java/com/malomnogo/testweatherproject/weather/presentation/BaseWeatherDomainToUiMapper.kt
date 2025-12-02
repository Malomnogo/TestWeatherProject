package com.malomnogo.testweatherproject.weather.presentation

import com.malomnogo.testweatherproject.weather.domain.WeatherDomain

class BaseWeatherDomainToUiMapper(
    private val formatWeather: FormatWeather
) : WeatherDomain.Mapper<WeatherUiState> {

    override fun mapSuccess(
        city: String,
        temperature: Double
    ) = WeatherUiState.Success(
        city = city,
        temperature = formatWeather.formatWeather(temperature)
    )

    override fun mapError(message: String) = WeatherUiState.Error(message = message)
}