package com.malomnogo.presentation.weather

import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.ForecastDomain
import com.malomnogo.presentation.core.FormatDate

class BaseForecastDomainToUiMapper(
    private val forecastDayMapper: ForecastDayDomain.Mapper<ForecastDayUiState>
) : ForecastDomain.Mapper<ForecastUiState> {

    override fun mapSuccess(forecastDay: List<ForecastDayDomain>) = ForecastUiState.Base(
        forecastDay = forecastDay.map { it.map(forecastDayMapper) }
    )

    override fun mapError(message: String) = ForecastUiState.Empty
}