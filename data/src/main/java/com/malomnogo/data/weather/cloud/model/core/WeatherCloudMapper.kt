package com.malomnogo.data.weather.cloud.model.core

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.forecast.ForecastCloud
import com.malomnogo.data.weather.cloud.model.forecast.ForecastCloudMapper
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.location.LocationCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.temperature.CurrentTemperatureCloud
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloudMapper
import com.malomnogo.domain.ForecastDomain
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain

interface WeatherCloudMapper<T : Any> {

    fun map(
        location: LocationCloud?,
        current: CurrentTemperatureCloud?,
        forecast: ForecastCloud?
    ): T

    class ToDomain(
        private val locationRemoteMapper: LocationCloudRemoteMapper<String>,
        private val temperatureCloudMapper: TemperatureCloudMapper<TemperatureDomain>,
        private val forecastCloudMapper: ForecastCloudMapper<ForecastDomain>,
        private val provideResources: ProvideResources
    ) : WeatherCloudMapper<WeatherDomain> {

        override fun map(
            location: LocationCloud?,
            current: CurrentTemperatureCloud?,
            forecast: ForecastCloud?
        ): WeatherDomain {
            val cityDomain = location?.map(locationRemoteMapper)
            val temperatureDomain = current?.map(temperatureCloudMapper)
            val forecastDomain = forecast?.map(forecastCloudMapper)

            return if (cityDomain == null || temperatureDomain == null || forecastDomain == null)
                WeatherDomain.Error(message = provideResources.serviceSentUnknownData())
            else
                WeatherDomain.Success(
                    city = cityDomain,
                    temperature = temperatureDomain,
                    forecast = forecastDomain
                )
        }
    }
}