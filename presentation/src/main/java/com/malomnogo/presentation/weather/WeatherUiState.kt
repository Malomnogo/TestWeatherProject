package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.views.ChangeVisibility
import com.malomnogo.presentation.core.views.ShowError
import com.malomnogo.presentation.weather.views.ShowWeather

interface WeatherUiState {

    fun update(
        progressBar: ChangeVisibility,
        errorView: ShowError,
        weatherLayout: ShowWeather
    )

    abstract class Abstract(
        private val progressVisibility: Boolean = false,
        private val errorVisibility: Boolean = false,
        private val weatherVisibility: Boolean = false,
        private val city: String = "",
        private val temperature: String = "",
        private val errorMessage: String = ""
    ) : WeatherUiState {

        override fun update(
            progressBar: ChangeVisibility,
            errorView: ShowError,
            weatherLayout: ShowWeather,
        ) {
            progressBar.change(progressVisibility)
            errorView.change(errorVisibility)

            if (weatherVisibility && city.isNotEmpty() && temperature.isNotEmpty()) {
                weatherLayout.showCity(city)
                weatherLayout.showTemperature(temperature)
            }
            weatherLayout.change(weatherVisibility)

            if (errorVisibility && errorMessage.isNotEmpty()) {
                errorView.showError(errorMessage)
            }
        }
    }

    data object Initial : Abstract()

    data object Progress : Abstract(progressVisibility = true)

    data class Success(
        private val city: String,
        private val temperature: String
    ) : Abstract(
        weatherVisibility = true,
        city = city,
        temperature = temperature
    )

    data class Error(
        private val message: String
    ) : Abstract(
        errorVisibility = true,
        errorMessage = message
    )
}