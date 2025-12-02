package com.malomnogo.testweatherproject.weather.modules

import com.malomnogo.testweatherproject.weather.data.MockWeatherRepository
import com.malomnogo.testweatherproject.weather.presentation.BaseWeatherDomainToUiMapper
import com.malomnogo.testweatherproject.weather.presentation.FormatWeather
import com.malomnogo.testweatherproject.weather.presentation.UiObservable
import com.malomnogo.testweatherproject.weather.presentation.WeatherViewModel

class WeatherModule(
    private val core: Core
) : Module<WeatherViewModel> {

    override fun viewModel() = WeatherViewModel(
        runAsync = core.provideRunAsync(),
        uiObservable = UiObservable.Base(),
        repository = MockWeatherRepository(),
        mapper = BaseWeatherDomainToUiMapper(formatWeather = FormatWeather.Base())
    )
}