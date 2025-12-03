package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.views.ShowDayForecast

interface DailyForecastUiState {

    fun update(dayForecastView: ShowDayForecast)

    data object Empty : DailyForecastUiState {
        override fun update(dayForecastView: ShowDayForecast) {
            dayForecastView.change(false)
        }
    }

    data class Base(
        private val dailyForecast: List<DayForecastUiState>
    ) : DailyForecastUiState {

        override fun update(dayForecastView: ShowDayForecast) {
            dayForecastView.showDailyForecast(dailyForecast)
            dayForecastView.change(true)
        }
    }
}

