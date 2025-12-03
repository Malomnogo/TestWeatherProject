package com.malomnogo.testweatherproject.weather

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.core.ProvideApiKey
import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.BaseWeatherRepository
import com.malomnogo.data.weather.cloud.WeatherCloudDataSource
import com.malomnogo.data.weather.cloud.model.core.WeatherCloudMapper
import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.DayDomain
import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.ForecastDomain
import com.malomnogo.domain.HourDomain
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain
import com.malomnogo.domain.WeatherRepository

interface ProvideInstance {

    fun provideWeatherRepository(
        cloudDataSource: WeatherCloudDataSource,
        handleError: HandleError,
        provideResources: ProvideResources,
        weatherMapper: WeatherCloudMapper<WeatherDomain>
    ): WeatherRepository

    fun provideApiKey(): ProvideApiKey

    class Base : ProvideInstance {

        override fun provideWeatherRepository(
            cloudDataSource: WeatherCloudDataSource,
            handleError: HandleError,
            provideResources: ProvideResources,
            weatherMapper: WeatherCloudMapper<WeatherDomain>
        ): WeatherRepository {

            return BaseWeatherRepository(
                cloudDataSource = cloudDataSource,
                handleError = handleError,
                weatherMapper = weatherMapper
            )
        }

        override fun provideApiKey() = BaseProvideApiKey()
    }

    class Mock : ProvideInstance {

        override fun provideWeatherRepository(
            cloudDataSource: WeatherCloudDataSource,
            handleError: HandleError,
            provideResources: ProvideResources,
            weatherMapper: WeatherCloudMapper<WeatherDomain>
        ) = MockWeatherRepository()

        override fun provideApiKey() = MockProvideApiKey()

        class MockProvideApiKey : ProvideApiKey {
            override fun weatherApiKey() = ""
        }

        class MockWeatherRepository : WeatherRepository {

            private var attempts: Int = 0

            override suspend fun loadData(): WeatherDomain {
                attempts++
                return if (attempts < 2) {
                    WeatherDomain.Error(message = "No internet connection")
                } else {
                    val hourlyForecast = listOf(
                        HourDomain.Success(timeEpoch = 1733011200L, tempC = 0.0),
                        HourDomain.Success(timeEpoch = 1733014800L, tempC = 1.0),
                        HourDomain.Success(timeEpoch = 1733018400L, tempC = 2.0)
                    )
                    
                    val dailyForecast = listOf(
                        ForecastDayDomain.Success(
                            dateEpoch = 1733011200L,
                            day = DayDomain.Success(
                                maxTempC = 1.0,
                                minTempC = -1.0,
                                conditionDomain = ConditionDomain.Success(text = "Sunny", iconUrl = "http://icon1.png")
                            ),
                            hour = hourlyForecast
                        ),
                        ForecastDayDomain.Success(
                            dateEpoch = 1733097600L,
                            day = DayDomain.Success(
                                maxTempC = 2.0,
                                minTempC = -2.0,
                                conditionDomain = ConditionDomain.Success(text = "Cloudy", iconUrl = "http://icon2.png")
                            ),
                            hour = emptyList()
                        ),
                        ForecastDayDomain.Success(
                            dateEpoch = 1733184000L,
                            day = DayDomain.Success(
                                maxTempC = 3.0,
                                minTempC = -3.0,
                                conditionDomain = ConditionDomain.Success(text = "Rainy", iconUrl = "http://icon3.png")
                            ),
                            hour = emptyList()
                        )
                    )
                    
                    WeatherDomain.Success(
                        city = "Moscow",
                        temperature = TemperatureDomain.Success(
                            temperature = 30.0,
                            condition = ConditionDomain.Success(text = "Sunny", iconUrl = "http://icon.png")
                        ),
                        forecast = ForecastDomain.Success(forecastDay = dailyForecast)
                    )
                }
            }
        }
    }
}