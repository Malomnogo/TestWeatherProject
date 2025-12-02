package com.malomnogo.testweatherproject.weather.modules

import com.malomnogo.data.weather.cloud.WeatherCloudDataSource
import com.malomnogo.data.weather.cloud.model.core.WeatherCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.location.LocationCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloudRemoteMapper
import com.malomnogo.presentation.weather.BaseWeatherDomainToUiMapper
import com.malomnogo.presentation.core.FormatWeather
import com.malomnogo.presentation.weather.BaseTemperatureMapper
import com.malomnogo.presentation.weather.WeatherUiObservable
import com.malomnogo.presentation.weather.WeatherViewModel
import com.malomnogo.testweatherproject.weather.ProvideInstance

class WeatherModule(
    private val core: Core,
    private val provideInstance: ProvideInstance
) : Module<WeatherViewModel> {

    override fun viewModel(): WeatherViewModel {
        val provideResources = core.provideResources()
        return WeatherViewModel(
            runAsync = core.provideRunAsync(),
            uiObservable = WeatherUiObservable.Base(),
            repository = provideInstance.provideWeatherRepository(
                cloudDataSource = WeatherCloudDataSource.Base(
                    provideApiKey = provideInstance.provideApiKey(),
                    retrofit = core.provideRetrofit()
                ),
                handleError = core.provideHandleError(),
                provideResources = provideResources,
                weatherMapper = WeatherCloudRemoteMapper.ToDomain(
                    locationRemoteMapper = LocationCloudRemoteMapper.ToDomain(provideResources),
                    temperatureCloudRemoteMapper = TemperatureCloudRemoteMapper.ToDomain(
                        provideResources
                    ),
                    provideResources = provideResources
                )
            ),
            mapper = BaseWeatherDomainToUiMapper(
                temperatureMapper = BaseTemperatureMapper(
                    formatWeather = FormatWeather.Base()
                )
            )
        )
    }
}