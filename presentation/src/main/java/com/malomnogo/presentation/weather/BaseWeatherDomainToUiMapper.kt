package com.malomnogo.presentation.weather

import com.malomnogo.domain.ForecastDomain
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain

class BaseWeatherDomainToUiMapper(
    private val temperatureMapper: TemperatureDomain.Mapper<TemperatureUiState>,
    private val forecastMapper: ForecastDomain.Mapper<ForecastUiState>
) : WeatherDomain.Mapper<WeatherUiState> {

    override fun mapSuccess(
        city: String,
        temperature: TemperatureDomain,
        forecast: ForecastDomain
    ) = WeatherUiState.Success(
        city = city,
        temperature = temperature.map(temperatureMapper),
        forecast = forecast.map(forecastMapper)
    )

    override fun mapError(message: String) = WeatherUiState.Error(message = message)
}