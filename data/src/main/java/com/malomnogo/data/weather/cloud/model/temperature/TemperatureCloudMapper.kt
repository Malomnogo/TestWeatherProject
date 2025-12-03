package com.malomnogo.data.weather.cloud.model.temperature

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloud
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloudMapper
import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.TemperatureDomain

interface TemperatureCloudMapper<T> {

    fun map(
        temperature: Double?,
        condition: TemperatureConditionCloud?
    ): T

    class ToDomain(
        private val provideResources: ProvideResources,
        private val conditionMapper: TemperatureConditionCloudMapper<ConditionDomain>
    ) : TemperatureCloudMapper<TemperatureDomain> {

        override fun map(temperature: Double?, condition: TemperatureConditionCloud?) =
            if (temperature == null || condition == null)
                TemperatureDomain.Error(message = provideResources.serviceSentUnknownData())
            else
                TemperatureDomain.Success(
                    temperature = temperature,
                    condition = condition.map(conditionMapper)
                )
    }
}