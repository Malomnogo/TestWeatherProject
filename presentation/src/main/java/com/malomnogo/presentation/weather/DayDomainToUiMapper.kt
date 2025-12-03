package com.malomnogo.presentation.weather

import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.DayDomain
import com.malomnogo.presentation.core.FormatWeather

class DayDomainToUiMapper(
    private val formatWeather: FormatWeather,
    private val conditionMapper: ConditionDomain.Mapper<String>
) : DayDomain.Mapper<DayUiState> {

    override fun mapSuccess(
        maxTempC: Double,
        minTempC: Double,
        condition: ConditionDomain
    ) = DayUiState.Base(
        maxTemperature = "max: ${formatWeather.formatWeather(temperature = maxTempC)}",
        minTemperature = "min: ${formatWeather.formatWeather(temperature = minTempC)}",
        iconUrl = condition.map(conditionMapper)
    )

    override fun mapError(message: String) = DayUiState.Empty
}