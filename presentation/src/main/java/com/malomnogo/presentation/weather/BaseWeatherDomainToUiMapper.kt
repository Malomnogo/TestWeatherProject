package com.malomnogo.presentation.weather

import com.malomnogo.domain.WeatherDomain
import com.malomnogo.presentation.weather.WeatherUiState
import com.malomnogo.presentation.core.FormatWeather

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