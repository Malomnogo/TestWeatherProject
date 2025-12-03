package com.malomnogo.presentation.weather

import com.malomnogo.domain.HourDomain
import com.malomnogo.presentation.core.FormatTime
import com.malomnogo.presentation.core.FormatWeather

class BaseHourDomainMapper(
    private val formatWeather: FormatWeather,
    private val formatTime: FormatTime
) : HourDomain.Mapper<HourUiState> {

    override fun mapSuccess(timeEpoch: Long, tempC: Double) = HourUiState.Base(
        time = formatTime.formatTime(timeEpoch),
        temperature = formatWeather.formatWeather(tempC)
    )

    override fun mapError(message: String) = HourUiState.Empty
}
