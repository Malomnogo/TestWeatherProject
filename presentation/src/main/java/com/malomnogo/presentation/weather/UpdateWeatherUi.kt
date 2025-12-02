package com.malomnogo.presentation.weather

interface UpdateWeatherUi {

    fun updateUi(uiState: WeatherUiState)

    object Empty : UpdateWeatherUi {
        override fun updateUi(uiState: WeatherUiState) = Unit
    }
}