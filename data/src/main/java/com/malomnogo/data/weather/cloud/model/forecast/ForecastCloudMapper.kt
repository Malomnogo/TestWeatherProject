package com.malomnogo.data.weather.cloud.model.forecast

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.forecastDay.ForecastDayCloud
import com.malomnogo.data.weather.cloud.model.forecastDay.ForecastDayCloudMapper
import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.ForecastDomain

interface ForecastCloudMapper<T> {

    fun map(forecastDay: List<ForecastDayCloud>?): T

    class ToDomain(
        private val provideResources: ProvideResources,
        private val forecastDayMapper: ForecastDayCloudMapper<ForecastDayDomain>
    ) : ForecastCloudMapper<ForecastDomain> {

        override fun map(forecastDay: List<ForecastDayCloud>?) = if (forecastDay == null)
            ForecastDomain.Error(message = provideResources.serviceSentUnknownData())
        else
            ForecastDomain.Success(
                forecastDay = forecastDay.map { it.map(forecastDayMapper) }
            )
    }
}