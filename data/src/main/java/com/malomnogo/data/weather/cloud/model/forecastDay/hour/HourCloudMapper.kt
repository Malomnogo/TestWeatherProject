package com.malomnogo.data.weather.cloud.model.forecastDay.hour

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.domain.HourDomain

interface HourCloudMapper<T> {

    fun map(
        timeEpoch: Long?,
        tempC: Double?
    ): T

    class ToDomain(
        private val provideResources: ProvideResources
    ) : HourCloudMapper<HourDomain> {

        override fun map(
            timeEpoch: Long?,
            tempC: Double?
        ) = if (timeEpoch == null || tempC == null)
            HourDomain.Error(
                message = provideResources.serviceSentUnknownData()
            )
        else
            HourDomain.Success(
                timeEpoch = timeEpoch,
                tempC = tempC
            )
    }
}