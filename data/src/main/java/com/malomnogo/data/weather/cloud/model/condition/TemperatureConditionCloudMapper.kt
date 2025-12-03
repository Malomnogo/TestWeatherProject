package com.malomnogo.data.weather.cloud.model.condition

import com.malomnogo.data.core.ProvideResources
import com.malomnogo.domain.ConditionDomain

interface TemperatureConditionCloudMapper<T : Any> {

    fun map(
        text: String?,
        icon: String?,
    ): T

    class ToDomain(
        private val provideResources: ProvideResources
    ) : TemperatureConditionCloudMapper<ConditionDomain> {

        override fun map(text: String?, icon: String?) = if (text != null && icon != null)
            ConditionDomain.Success(text = text, iconUrl = "http:$icon")
        else
            ConditionDomain.Error(message = provideResources.serviceSentUnknownData())
    }
}