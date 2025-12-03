package com.malomnogo.presentation.weather

import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.DayDomain
import com.malomnogo.presentation.core.FormatWeather

class BaseDayDomainMapper(
    private val formatWeather: FormatWeather,
    private val conditionMapper: ConditionDomain.Mapper<String>
) : DayDomain.Mapper<DayUiState> {

    override fun mapSuccess(
        maxTempC: Double,
        minTempC: Double,
        conditionDomain: ConditionDomain
    ) = DayUiState.Base(
        date = "",
        maxTemperature = formatWeather.formatWeather(maxTempC),
        minTemperature = formatWeather.formatWeather(minTempC),
        iconUrl = conditionDomain.map(conditionMapper)
    )

    override fun mapError(message: String) = DayUiState.Empty
}
