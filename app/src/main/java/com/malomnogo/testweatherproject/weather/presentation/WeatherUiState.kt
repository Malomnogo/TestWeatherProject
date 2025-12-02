package com.malomnogo.testweatherproject.weather.presentation

interface WeatherUiState {

    fun update()

    data object Initial : WeatherUiState {

        override fun update() {
            //todo
        }
    }

    data object Progress : WeatherUiState {

        override fun update() {
            //todo
        }
    }

    data class Success(
        private val city: String,
        private val temperature: String
    ) : WeatherUiState {

        override fun update() {
            //todo
        }
    }

    data class Error(
        private val message: String
    ) : WeatherUiState {

        override fun update() {
            //todo
        }
    }

    data object Empty : WeatherUiState {

        override fun update() {
            //todo
        }
    }
}