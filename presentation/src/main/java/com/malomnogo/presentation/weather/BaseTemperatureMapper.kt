package com.malomnogo.presentation.weather

import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.presentation.core.FormatWeather

class BaseTemperatureMapper(
    private val formatWeather: FormatWeather
) : TemperatureDomain.Mapper<String> {

    override fun mapSuccess(temperature: Double) = formatWeather.formatWeather(temperature)

    override fun mapError(message: String) = message
}