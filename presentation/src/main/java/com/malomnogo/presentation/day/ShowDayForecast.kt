package com.malomnogo.presentation.day

import com.malomnogo.presentation.core.views.ChangeVisibility

interface ShowDayForecast : ChangeVisibility {

    fun showDailyForecast(dailyForecast: List<DayForecastUiState>)
}