package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility

interface ShowHour : ChangeVisibility {

    fun showTime(time: String)

    fun showTemperature(temperature: String)
}