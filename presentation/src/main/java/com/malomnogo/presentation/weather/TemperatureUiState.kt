package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.weather.views.ShowTemperature

interface TemperatureUiState {

    fun update(temperatureView: ShowTemperature)

    data object Empty : TemperatureUiState {
        override fun update(temperatureView: ShowTemperature) {
            temperatureView.change(false)
        }
    }

    data class Base(
        private val temperature: String,
        private val iconUrl: String
    ) : TemperatureUiState {

        override fun update(temperatureView: ShowTemperature) {
            temperatureView.showTemperature(temperature)
            temperatureView.showIcon(iconUrl)
            temperatureView.change(true)
        }
    }
}

