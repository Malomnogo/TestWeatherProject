package com.malomnogo.data.weather

import com.malomnogo.data.FakeHandleError
import com.malomnogo.data.Order
import com.malomnogo.data.weather.cloud.WeatherCloudDataSource
import com.malomnogo.data.weather.cloud.model.core.WeatherCloud
import com.malomnogo.data.weather.cloud.model.core.WeatherCloudRemoteMapper
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val WEATHER_CLOUD_DATA_SOURCE_FETCH_FORECAST = "WeatherCloudDataSource#fetchForecast"
private const val WEATHER_CLOUD_REMOTE_MAPPER_MAP = "WeatherCloudRemoteMapper#map"
private const val HANDLE_ERROR_HANDLE_ERROR = "HandleError#handleError"

class BaseWeatherRepositoryTest {

    private lateinit var order: Order
    private lateinit var fakeCloudDataSource: FakeWeatherCloudDataSource
    private lateinit var fakeHandleError: FakeHandleError
    private lateinit var fakeWeatherMapper: FakeWeatherCloudRemoteMapper
    private lateinit var repository: BaseWeatherRepository

    @Before
    fun setup() {
        order = Order()
        fakeCloudDataSource = FakeWeatherCloudDataSource(order)
        fakeHandleError = FakeHandleError(order)
        fakeWeatherMapper = FakeWeatherCloudRemoteMapper(order)
        repository = BaseWeatherRepository(
            cloudDataSource = fakeCloudDataSource,
            handleError = fakeHandleError,
            weatherMapper = fakeWeatherMapper
        )
    }

    @Test
    fun testSuccess() = runBlocking {
        val expectedWeatherDomain = WeatherDomain.Success(
            city = "Moscow",
            temperature = TemperatureDomain.Success(temperature = 30.0)
        )
        fakeWeatherMapper.result = expectedWeatherDomain
        fakeCloudDataSource.result = WeatherCloud(
            location = null,
            current = null
        )

        val result = repository.loadData()

        assertEquals(expectedWeatherDomain, result)
        assertEquals(
            Order(
                mutableListOf(
                    WEATHER_CLOUD_DATA_SOURCE_FETCH_FORECAST,
                    WEATHER_CLOUD_REMOTE_MAPPER_MAP
                )
            ),
            order
        )
    }

    @Test
    fun testError() = runBlocking {
        val exception = Exception("Network error")
        fakeCloudDataSource.exception = exception
        fakeHandleError.result = "Error message"

        val result = repository.loadData()

        assertEquals(
            WeatherDomain.Error(message = "Error message"),
            result
        )
        assertEquals(
            Order(
                mutableListOf(
                    WEATHER_CLOUD_DATA_SOURCE_FETCH_FORECAST,
                    HANDLE_ERROR_HANDLE_ERROR
                )
            ),
            order
        )
    }
}

private class FakeWeatherCloudDataSource(
    private val order: Order
) : WeatherCloudDataSource {

    var result: WeatherCloud? = null
    var exception: Exception? = null

    override suspend fun fetchForecast(
        coordinates: String,
        days: Int
    ): WeatherCloud {
        order.add(WEATHER_CLOUD_DATA_SOURCE_FETCH_FORECAST)
        exception?.let { throw it }
        return result!!
    }
}

private class FakeWeatherCloudRemoteMapper(
    private val order: Order
) : WeatherCloudRemoteMapper<WeatherDomain> {

    var result: WeatherDomain = WeatherDomain.Error(message = "")

    override fun map(
        location: com.malomnogo.data.weather.cloud.model.location.LocationCloud?,
        current: com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloud?
    ): WeatherDomain {
        order.add(WEATHER_CLOUD_REMOTE_MAPPER_MAP)
        return result
    }
}

