package com.malomnogo.presentation.weather

import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.ForecastDomain
import com.malomnogo.domain.HourDomain
import com.malomnogo.presentation.core.FormatDate

class BaseForecastDomainToUiMapper(
    private val forecastDayMapper: BaseForecastDayDomainMapper,
    private val hourMapper: HourDomain.Mapper<HourUiState>,
    private val dayMapper: com.malomnogo.domain.DayDomain.Mapper<DayUiState>,
    private val formatDate: FormatDate
) : ForecastDomain.Mapper<ForecastUiState> {

    override fun mapSuccess(forecastDay: List<ForecastDayDomain>) = ForecastUiState.Base(
        forecastDay = forecastDay.map { it.map(forecastDayMapper) },
        hourMapper = hourMapper,
        dayMapper = dayMapper,
        formatDate = formatDate
    )

    override fun mapError(message: String) = ForecastUiState.Empty
}
