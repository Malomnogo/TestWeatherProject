package com.malomnogo.presentation.day

import com.malomnogo.presentation.weather.views.ShowDay

interface DayUiState {

    fun update(dayView: ShowDay)

    data object Empty : DayUiState {

        override fun update(dayView: ShowDay) {
            dayView.change(false)
        }
    }

    data class Base(
        private val maxTemperature: String,
        private val minTemperature: String,
        private val iconUrl: String
    ) : DayUiState {

        override fun update(dayView: ShowDay) {
            dayView.showMaxTemperature(maxTemperature)
            dayView.showMinTemperature(minTemperature)
            dayView.showIcon(iconUrl)
            dayView.change(true)
        }
    }
}