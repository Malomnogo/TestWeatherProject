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
                    val baseTimeEpoch = 1764536400L // 1.12.2025 00:00
                    val hourlyForecast = (0..2).map { index ->
                        HourDomain.Success(
                            timeEpoch = baseTimeEpoch + index * 3600L,
                            tempC = index.toDouble()
                        )
                    }
                    
                    val dailyForecast = (0..2).map { index ->
                        ForecastDayDomain.Success(
                            dateEpoch = baseTimeEpoch + index * 86400L,
                            day = DayDomain.Success(
                                maxTempC = (index + 1).toDouble(),
                                minTempC = -(index + 1).toDouble(),
                                condition = ConditionDomain.Success(
                                    text = "Sunny",
                                    iconUrl = "http://icon$index.png"
                                )
                            ),
                            hour = if (index == 0) hourlyForecast else emptyList()
                        )
                    }
                    
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