package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.DayForecastUiState

interface ShowDayForecast : ChangeVisibility {

    fun showTitle(title: String)

    fun showDailyForecast(dailyForecast: List<DayForecastUiState>)
}