package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.UpdateObserver
import com.malomnogo.presentation.weather.UpdateWeatherUi

interface WeatherUiObservable : UpdateWeatherUi, UpdateObserver {

    fun clear()

    class Base : WeatherUiObservable {

        private var cache: WeatherUiState = WeatherUiState.Initial
        private var observer: UpdateWeatherUi = UpdateWeatherUi.Empty

        override fun clear() {
            cache = WeatherUiState.Initial
        }

        override fun updateUi(uiState: WeatherUiState) {
            cache = uiState
            observer.updateUi(cache)
        }

        override fun updateObserver(observer: UpdateWeatherUi) {
            this.observer = observer
            observer.updateUi(cache)
        }
    }
}