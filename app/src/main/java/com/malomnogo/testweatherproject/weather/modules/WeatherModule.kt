package com.malomnogo.testweatherproject.weather.modules

import com.malomnogo.data.MockWeatherRepository
import com.malomnogo.presentation.weather.BaseWeatherDomainToUiMapper
import com.malomnogo.presentation.core.FormatWeather
import com.malomnogo.presentation.weather.WeatherUiObservable
import com.malomnogo.presentation.weather.WeatherViewModel

class WeatherModule(
    private val core: Core
) : Module<WeatherViewModel> {

    override fun viewModel() = WeatherViewModel(
        runAsync = core.provideRunAsync(),
        uiObservable = WeatherUiObservable.Base(),
        repository = MockWeatherRepository(),
        mapper = BaseWeatherDomainToUiMapper(formatWeather = FormatWeather.Base())
    )
}