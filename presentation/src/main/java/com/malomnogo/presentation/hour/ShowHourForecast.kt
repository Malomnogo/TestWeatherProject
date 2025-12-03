package com.malomnogo.presentation.hour

import com.malomnogo.presentation.core.views.ChangeVisibility

interface ShowHourForecast : ChangeVisibility {

    fun showHourlyForecast(hourlyForecast: List<HourUiState>)
}