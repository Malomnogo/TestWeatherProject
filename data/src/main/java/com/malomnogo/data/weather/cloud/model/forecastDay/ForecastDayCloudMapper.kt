package com.malomnogo.data.weather.cloud.model.forecastDay

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.forecastDay.day.DayCloud
import com.malomnogo.data.weather.cloud.model.forecastDay.day.DayCloudMapper
import com.malomnogo.data.weather.cloud.model.forecastDay.hour.HourCloud
import com.malomnogo.data.weather.cloud.model.forecastDay.hour.HourCloudMapper
import com.malomnogo.domain.DayDomain
import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.HourDomain

interface ForecastDayCloudMapper<T> {

    fun map(
        dateEpoch: Long?,
        day: DayCloud?,
        hour: List<HourCloud>?
    ): T

    class ToDomain(
        private val provideResources: ProvideResources,
        private val dayMapper: DayCloudMapper<DayDomain>,
        private val hourMapper: HourCloudMapper<HourDomain>
    ) : ForecastDayCloudMapper<ForecastDayDomain> {

        override fun map(
            dateEpoch: Long?,
            day: DayCloud?,
            hour: List<HourCloud>?
        ) = if (dateEpoch == null || day == null || hour == null)
            ForecastDayDomain.Error(
                message = provideResources.serviceSentUnknownData()
            )
        else
            ForecastDayDomain.Success(
                dateEpoch = dateEpoch,
                day = day.map(dayMapper),
                hour = hour.map { it.map(hourMapper) }
            )
    }
}