package com.malomnogo.testweatherproject.weather

import com.malomnogo.data.core.ProvideApiKey
import com.malomnogo.testweatherproject.BuildConfig

class BaseProvideApiKey : ProvideApiKey {

    override fun weatherApiKey(): String = BuildConfig.WEATHER_API_KEY
}