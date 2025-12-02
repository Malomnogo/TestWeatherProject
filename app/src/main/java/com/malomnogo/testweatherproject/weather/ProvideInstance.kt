package com.malomnogo.testweatherproject.weather

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.core.ProvideApiKey
import com.malomnogo.data.core.ProvideResources
import com.malomnogo.data.weather.BaseWeatherRepository
import com.malomnogo.data.weather.cloud.WeatherCloudDataSource
import com.malomnogo.data.weather.cloud.model.core.WeatherCloudRemoteMapper
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain
import com.malomnogo.domain.WeatherRepository

interface ProvideInstance {

    fun provideWeatherRepository(
        cloudDataSource: WeatherCloudDataSource,
        handleError: HandleError,
        provideResources: ProvideResources,
        weatherMapper: WeatherCloudRemoteMapper<WeatherDomain>
    ): WeatherRepository

    fun provideApiKey(): ProvideApiKey

    class Base : ProvideInstance {

        override fun provideWeatherRepository(
            cloudDataSource: WeatherCloudDataSource,
            handleError: HandleError,
            provideResources: ProvideResources,
            weatherMapper: WeatherCloudRemoteMapper<WeatherDomain>
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
            weatherMapper: WeatherCloudRemoteMapper<WeatherDomain>
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
                    WeatherDomain.Success(
                        city = "Moscow",
                        temperature = TemperatureDomain.Success(temperature = 30.0)
                    )
                }
            }
        }
    }
}