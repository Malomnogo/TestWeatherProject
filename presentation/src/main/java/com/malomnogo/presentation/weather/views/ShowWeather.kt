package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.ForecastUiState
import com.malomnogo.presentation.weather.TemperatureUiState

interface ShowWeather : ChangeVisibility {

    fun showCity(city: String)

    fun showTemperature(temperature: TemperatureUiState)

    fun showHourForecast(hourForecast: ForecastUiState.Hour)

    fun showDayForecast(dayForecast: ForecastUiState.Day)
}