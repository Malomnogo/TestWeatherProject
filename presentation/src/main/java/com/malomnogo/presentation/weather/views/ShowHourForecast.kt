package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.HourUiState

interface ShowHourForecast : ChangeVisibility {

    fun showTitle(title: String)

    fun showHourlyForecast(hourlyForecast: List<HourUiState>)
}