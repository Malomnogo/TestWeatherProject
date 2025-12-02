package com.malomnogo.data.weather.cloud.model.temperature

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.domain.TemperatureDomain

interface TemperatureCloudRemoteMapper<T> {

    fun map(temperature: Double?): T

    class ToDomain(
        private val provideResources: ProvideResources
    ) : TemperatureCloudRemoteMapper<TemperatureDomain> {

        override fun map(temperature: Double?) = if (temperature == null)
            TemperatureDomain.Error(message = provideResources.serviceSentUnknownData())
        else
            TemperatureDomain.Success(temperature = temperature)
    }
}

