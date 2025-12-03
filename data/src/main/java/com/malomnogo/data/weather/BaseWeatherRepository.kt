package com.malomnogo.data.weather

import com.malomnogo.data.core.HandleError
import com.malomnogo.data.weather.cloud.WeatherCloudDataSource
import com.malomnogo.data.weather.cloud.model.core.WeatherCloudMapper
import com.malomnogo.domain.WeatherDomain
import com.malomnogo.domain.WeatherRepository

class BaseWeatherRepository(
    private val cloudDataSource: WeatherCloudDataSource,
    private val handleError: HandleError,
    private val weatherMapper: WeatherCloudMapper<WeatherDomain>
) : WeatherRepository {

    override suspend fun loadData(): WeatherDomain {
        return try {
            //hardcoded in this case
            val result = cloudDataSource.fetchForecast(
                coordinates = "55.7569,37.6151",
                days = 3
            )
            result.map(weatherMapper)
        } catch (e: Exception) {
            WeatherDomain.Error(message = handleError.handleError(exception = e))
        }
    }
}