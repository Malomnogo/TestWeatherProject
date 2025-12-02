package com.malomnogo.presentation.core

import com.malomnogo.presentation.weather.UpdateWeatherUi

interface UpdateObserver {

    fun updateObserver(observer: UpdateWeatherUi)
}