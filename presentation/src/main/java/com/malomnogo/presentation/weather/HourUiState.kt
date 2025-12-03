package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.views.ShowHour

interface HourUiState {

    fun update(hourView: ShowHour)

    data object Empty : HourUiState {
        override fun update(hourView: ShowHour) {
            hourView.change(false)
        }
    }

    data class Base(
        private val time: String,
        private val temperature: String
    ) : HourUiState {

        override fun update(hourView: ShowHour) {
            hourView.showTime(time)
            hourView.showTemperature(temperature)
            hourView.change(true)
        }
    }
}
