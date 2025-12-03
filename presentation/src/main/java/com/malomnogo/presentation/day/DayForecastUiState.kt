package com.malomnogo.presentation.day

import com.malomnogo.presentation.weather.views.ShowDay

interface DayForecastUiState {

    fun update(dayForecastView: ShowDay)

    data object Empty : DayForecastUiState {

        override fun update(dayForecastView: ShowDay) {
            dayForecastView.change(false)
        }
    }

    data class Base(
        private val date: String,
        private val temperature: DayUiState
    ) : DayForecastUiState {

        override fun update(dayForecastView: ShowDay) {
            dayForecastView.showDate(date)
            temperature.update(dayForecastView)
            dayForecastView.change(true)
        }
    }
}