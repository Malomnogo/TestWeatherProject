package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility

interface ShowWeather : ChangeVisibility {

    fun showCity(city: String)

    fun showTemperature(temperature: String)
}