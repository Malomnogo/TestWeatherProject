package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.views.ShowHourForecast

interface HourForecastUiState {

    fun update(hourForecastView: ShowHourForecast)

    data object Empty : HourForecastUiState {
        override fun update(hourForecastView: ShowHourForecast) {
            hourForecastView.change(false)
        }
    }

    data class Base(
        private val title: String,
        private val hourlyForecast: List<HourUiState>
    ) : HourForecastUiState {

        override fun update(hourForecastView: ShowHourForecast) {
            hourForecastView.showTitle(title)
            hourForecastView.showHourlyForecast(hourlyForecast)
            hourForecastView.change(true)
        }
    }
}

