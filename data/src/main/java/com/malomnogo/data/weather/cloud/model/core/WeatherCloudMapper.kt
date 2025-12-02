package com.malomnogo.data.weather.cloud.model.core

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.location.LocationCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.temperature.CurrentTemperatureCloud
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloudMapper
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain

interface WeatherCloudMapper<T : Any> {

    fun map(
        location: LocationCloud?,
        current: CurrentTemperatureCloud?
    ): T

    class ToDomain(
        private val locationRemoteMapper: LocationCloudRemoteMapper<String>,
        private val temperatureCloudMapper: TemperatureCloudMapper<TemperatureDomain>,
        private val provideResources: ProvideResources
    ) : WeatherCloudMapper<WeatherDomain> {

        override fun map(
            location: LocationCloud?,
            current: CurrentTemperatureCloud?
        ): WeatherDomain {
            val city = location?.map(locationRemoteMapper)
            val temperature = current?.map(temperatureCloudMapper)

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