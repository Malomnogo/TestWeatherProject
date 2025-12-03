package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.HourUiState

interface ShowHourForecast : ShowHour, ChangeVisibility {

    fun showTitle(title: String)
}

