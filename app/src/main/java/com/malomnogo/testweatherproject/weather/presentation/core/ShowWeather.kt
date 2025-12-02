package com.malomnogo.testweatherproject.weather.presentation.core

interface ShowWeather : ChangeVisibility {

    fun showCity(city: String)

    fun showTemperature(temperature: String)
}

