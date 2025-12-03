package com.malomnogo.data.weather.cloud.model.forecastDay.day

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloud
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloudMapper
import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.DayDomain

interface DayCloudMapper<T> {

    fun map(
        maxTempC: Double?,
        minTempC: Double?,
        conditionDomain: TemperatureConditionCloud?
    ): T

    class ToDomain(
        private val provideResources: ProvideResources,
        private val conditionMapper: TemperatureConditionCloudMapper<ConditionDomain>
    ) : DayCloudMapper<DayDomain> {

        override fun map(
            maxTempC: Double?,
            minTempC: Double?,
            conditionDomain: TemperatureConditionCloud?
        ) = if (maxTempC == null || minTempC == null || conditionDomain == null)
            DayDomain.Error(
                message = provideResources.serviceSentUnknownData()
            )
        else
            DayDomain.Success(
                maxTempC = maxTempC,
                minTempC = minTempC,
                condition = conditionDomain.map(conditionMapper)
            )
    }
}