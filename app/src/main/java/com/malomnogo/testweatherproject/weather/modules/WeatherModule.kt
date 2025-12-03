package com.malomnogo.testweatherproject.weather.modules

import com.malomnogo.data.weather.cloud.WeatherCloudDataSource
import com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloudMapper
import com.malomnogo.data.weather.cloud.model.core.WeatherCloudMapper
import com.malomnogo.data.weather.cloud.model.forecast.ForecastCloudMapper
import com.malomnogo.data.weather.cloud.model.forecastDay.ForecastDayCloudMapper
import com.malomnogo.data.weather.cloud.model.forecastDay.day.DayCloudMapper
import com.malomnogo.data.weather.cloud.model.forecastDay.hour.HourCloudMapper
import com.malomnogo.data.weather.cloud.model.location.LocationCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloudMapper
import com.malomnogo.presentation.weather.BaseWeatherDomainToUiMapper
import com.malomnogo.presentation.core.FormatWeather
import com.malomnogo.presentation.core.FormatDate
import com.malomnogo.presentation.core.FormatTime
import com.malomnogo.presentation.weather.BaseTemperatureDomainMapper
import com.malomnogo.presentation.weather.BaseConditionDomainMapper
import com.malomnogo.presentation.weather.DayDomainToUiMapper
import com.malomnogo.presentation.weather.HourDomainToUiMapper
import com.malomnogo.presentation.weather.BaseForecastDayDomainMapper
import com.malomnogo.presentation.weather.BaseForecastDomainToUiMapper
import com.malomnogo.presentation.weather.WeatherUiObservable
import com.malomnogo.presentation.weather.WeatherViewModel
import com.malomnogo.testweatherproject.weather.ProvideInstance

class WeatherModule(
    private val core: Core,
    private val provideInstance: ProvideInstance
) : Module<WeatherViewModel> {

    override fun viewModel(): WeatherViewModel {
        val provideResources = core.provideResources()
        val conditionMapper = TemperatureConditionCloudMapper.ToDomain(provideResources)
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
                weatherMapper = WeatherCloudMapper.ToDomain(
                    locationRemoteMapper = LocationCloudRemoteMapper.ToDomain(provideResources),
                    temperatureCloudMapper = TemperatureCloudMapper.ToDomain(
                        provideResources = provideResources,
                        conditionMapper = conditionMapper
                    ),
                    provideResources = provideResources,
                    forecastCloudMapper = ForecastCloudMapper.ToDomain(
                        provideResources = provideResources,
                        forecastDayMapper = ForecastDayCloudMapper.ToDomain(
                            provideResources = provideResources,
                            dayMapper = DayCloudMapper.ToDomain(
                                provideResources = provideResources,
                                conditionMapper = conditionMapper
                            ),
                            hourMapper = HourCloudMapper.ToDomain(provideResources = provideResources)
                        )
                    )
                )
            ),
            mapper = BaseWeatherDomainToUiMapper(
                temperatureMapper = BaseTemperatureDomainMapper(
                    formatWeather = FormatWeather.Base(),
                    conditionMapper = BaseConditionDomainMapper()
                ),
                forecastMapper = BaseForecastDomainToUiMapper(
                    forecastDayMapper = BaseForecastDayDomainMapper(
                        formatDate = FormatDate.Base(),
                        dayMapper = DayDomainToUiMapper(
                            formatWeather = FormatWeather.Base(),
                            conditionMapper = BaseConditionDomainMapper()
                        ),
                        hourMapper = HourDomainToUiMapper(
                            formatTime = FormatTime.Base(),
                            formatWeather = FormatWeather.Base()
                        )
                    )
                )
            )
        )
    }
}