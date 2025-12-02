package com.malomnogo.testweatherproject.weather

import com.malomnogo.data.core.ApiKeyStorage
import com.malomnogo.testweatherproject.BuildConfig

object ProvideApiKeyStorage {

    fun apiKeyStorage(): ApiKeyStorage {
        return ApiKeyStorage.Base(
            apiKey = BuildConfig.WEATHER_API_KEY
        )
    }
}

