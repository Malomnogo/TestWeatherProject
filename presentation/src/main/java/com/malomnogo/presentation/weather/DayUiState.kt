package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.views.ShowDay

interface DayUiState {

    fun update(dayView: ShowDay)

    data object Empty : DayUiState {
        override fun update(dayView: ShowDay) {
            dayView.change(false)
        }
    }

    data class Base(
        private val date: String,
        private val maxTemperature: String,
        private val minTemperature: String,
        private val iconUrl: String
    ) : DayUiState {

        override fun update(dayView: ShowDay) {
            dayView.showDate(date)
            dayView.showMaxTemperature(maxTemperature)
            dayView.showMinTemperature(minTemperature)
            dayView.showIcon(iconUrl)
            dayView.change(true)
        }
    }
}
