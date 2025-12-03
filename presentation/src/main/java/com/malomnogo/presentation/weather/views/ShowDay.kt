package com.malomnogo.presentation.weather.views

import com.malomnogo.presentation.core.views.ChangeVisibility

interface ShowDay : ChangeVisibility {

    fun showDate(date: String)

    fun showMaxTemperature(maxTemperature: String)

    fun showMinTemperature(minTemperature: String)

    fun showIcon(iconUrl: String)
}
