package com.malomnogo.presentation.hour.mapper

import com.malomnogo.domain.HourDomain
import com.malomnogo.presentation.core.FormatTime
import com.malomnogo.presentation.core.FormatWeather
import com.malomnogo.presentation.hour.HourUiState

class HourDomainToUiMapper(
    private val formatTime: FormatTime,
    private val formatWeather: FormatWeather
) : HourDomain.Mapper<HourUiState> {

    override fun mapSuccess(
        timeEpoch: Long,
        tempC: Double
    ) = HourUiState.Base(
        time = formatTime.formatTime(timeEpoch),
        temperature = formatWeather.formatWeather(tempC)
    )

    override fun mapError(message: String) = HourUiState.Empty
}