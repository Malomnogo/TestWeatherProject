package com.malomnogo.testweatherproject.weather.presentation

interface UiObservable : UpdateUi, UpdateObserver {

    fun clear()

    class Base : UiObservable {

        private var cache: WeatherUiState = WeatherUiState.Initial
        private var observer: UpdateUi = UpdateUi.Empty

        override fun clear() {
            cache = WeatherUiState.Initial
        }

        override fun updateUi(uiState: WeatherUiState) {
            cache = uiState
            observer.updateUi(cache)
        }

        override fun updateObserver(observer: UpdateUi) {
            this.observer = observer
            observer.updateUi(cache)
        }
    }
}