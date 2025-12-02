package com.malomnogo.testweatherproject.weather.presentation

interface UpdateUi {

    fun updateUi(uiState: WeatherUiState)

    object Empty : UpdateUi {
        override fun updateUi(uiState: WeatherUiState) = Unit
    }
}