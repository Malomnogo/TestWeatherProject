package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.DayUiState

interface ShowDayForecast : ChangeVisibility {

    fun showDailyForecast(dailyForecast: List<DayUiState>)
}

