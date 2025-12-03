package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility

interface ShowTemperature : ChangeVisibility {

    fun showTemperature(temperature: String)

    fun showIcon(iconUrl: String)
}