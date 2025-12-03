package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.views.ShowDayForecast

interface DayForecastUiState {

    fun update(dayForecastView: ShowDayForecast)

    data object Empty : DayForecastUiState {
        override fun update(dayForecastView: ShowDayForecast) {
            dayForecastView.change(false)
        }
    }

    data class Base(
        private val dailyForecast: List<DayUiState>
    ) : DayForecastUiState {

        override fun update(dayForecastView: ShowDayForecast) {
            dailyForecast.forEach { dayState ->
                dayState.update(dayForecastView)
            }
            dayForecastView.change(true)
        }
    }
}

