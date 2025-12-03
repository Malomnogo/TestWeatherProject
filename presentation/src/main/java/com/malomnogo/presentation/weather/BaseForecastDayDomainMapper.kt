package com.malomnogo.presentation.weather

import com.malomnogo.domain.DayDomain
import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.HourDomain
import com.malomnogo.presentation.core.FormatDate

class BaseForecastDayDomainMapper(
    private val formatDate: FormatDate,
    private val dayMapper: DayDomain.Mapper<DayUiState>,
    private val hourMapper: HourDomain.Mapper<HourUiState>
) : ForecastDayDomain.Mapper<ForecastDayUiState> {

    override fun mapSuccess(
        dateEpoch: Long,
        day: DayDomain,
        hour: List<HourDomain>
    ) = ForecastDayUiState.Success(
        dateEpoch = formatDate.formatDate(dateEpoch),
        day = day.map(dayMapper),
        hour = hour.map { it.map(hourMapper) }
    )

    override fun mapError(message: String) = ForecastDayUiState.Error
}