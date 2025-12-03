package com.malomnogo.presentation.weather

import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.presentation.core.FormatWeather

class BaseTemperatureDomainMapper(
    private val formatWeather: FormatWeather,
    private val conditionMapper: ConditionDomain.Mapper<String>
) : TemperatureDomain.Mapper<TemperatureUiState> {

    override fun mapSuccess(temperature: Double, condition: ConditionDomain) =
        TemperatureUiState.Base(
            temperature = formatWeather.formatWeather(temperature),
            iconUrl = condition.map(conditionMapper)
        )

    override fun mapError(message: String) = TemperatureUiState.Empty
}