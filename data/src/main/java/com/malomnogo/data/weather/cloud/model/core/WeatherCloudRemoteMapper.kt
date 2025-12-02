package com.malomnogo.data.weather.cloud.model.core

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.location.LocationCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloud
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloudRemoteMapper
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain

interface WeatherCloudRemoteMapper<T : Any> {

    fun map(
        location: LocationCloud?,
        current: TemperatureCloud?
    ): T

    class ToDomain(
        private val locationRemoteMapper: LocationCloudRemoteMapper<String>,
        private val temperatureCloudRemoteMapper: TemperatureCloudRemoteMapper<TemperatureDomain>,
        private val provideResources: ProvideResources
    ) : WeatherCloudRemoteMapper<WeatherDomain> {

        override fun map(
            location: LocationCloud?,
            current: TemperatureCloud?
        ): WeatherDomain {
            val city = location?.map(locationRemoteMapper)
            val temperature = current?.map(temperatureCloudRemoteMapper)

            return if (city != null && temperature != null)
                WeatherDomain.Success(
                    city = city,
                    temperature = temperature
                )
            else
                WeatherDomain.Error(message = provideResources.serviceSentUnknownData())
        }
    }
}