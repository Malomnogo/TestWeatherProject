package com.malomnogo.data.weather.cloud

import com.malomnogo.data.Order
import com.malomnogo.data.core.ProvideApiKey
import com.malomnogo.data.weather.cloud.model.core.WeatherCloud
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.temperature.CurrentTemperatureCloud
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

private const val WEATHER_SERVICE_FETCH_FORECAST = "WeatherService#fetchForecast"
private const val PROVIDE_API_KEY_WEATHER_API_KEY = "ProvideApiKey#weatherApiKey"

class WeatherCloudDataSourceTest {

    private lateinit var order: Order
    private lateinit var fakeService: FakeWeatherService
    private lateinit var fakeProvideApiKey: FakeProvideApiKey
    private lateinit var dataSource: WeatherCloudDataSource.Base

    @Before
    fun setup() {
        order = Order()
        fakeService = FakeWeatherService(order)
        fakeProvideApiKey = FakeProvideApiKey(order)
        dataSource = WeatherCloudDataSource.Base(
            service = fakeService,
            provideApiKey = fakeProvideApiKey
        )
    }

    @Test
    fun testFetchForecast() = runBlocking {
        val expectedWeatherCloud = WeatherCloud(
            location = LocationCloud(name = "Moscow"),
            current = CurrentTemperatureCloud(tempC = 30.0)
        )
        fakeService.result = expectedWeatherCloud
        fakeProvideApiKey.apiKey = "test-api-key"

        val result = dataSource.fetchForecast(
            coordinates = "55.7569,37.6151",
            days = 3
        )

        assertEquals(expectedWeatherCloud, result)
        assertEquals("test-api-key", fakeService.capturedKey)
        assertEquals("55.7569,37.6151", fakeService.capturedCoordinates)
        assertEquals(3, fakeService.capturedDays)
        assertEquals(
            Order(
                mutableListOf(
                    PROVIDE_API_KEY_WEATHER_API_KEY,
                    WEATHER_SERVICE_FETCH_FORECAST
                )
            ),
            order
        )
    }
}

private class FakeWeatherService(
    private val order: Order
) : WeatherService {

    var result: WeatherCloud? = null
    var capturedKey: String = ""
    var capturedCoordinates: String = ""
    var capturedDays: Int = 0

    override fun fetchForecast(
        key: String,
        coordinates: String,
        days: Int
    ): Call<WeatherCloud> {
        order.add(WEATHER_SERVICE_FETCH_FORECAST)
        capturedKey = key
        capturedCoordinates = coordinates
        capturedDays = days
        return FakeCall(result!!)
    }
}

private class FakeProvideApiKey(
    private val order: Order
) : ProvideApiKey {

    var apiKey: String = ""

    override fun weatherApiKey(): String {
        order.add(PROVIDE_API_KEY_WEATHER_API_KEY)
        return apiKey
    }
}

private class FakeCall<T>(private val body: T) : Call<T> {
    override fun execute(): Response<T> = Response.success(body)
    override fun enqueue(callback: retrofit2.Callback<T>) = throw NotImplementedError()
    override fun cancel() = throw NotImplementedError()
    override fun isExecuted() = throw NotImplementedError()
    override fun isCanceled() = throw NotImplementedError()
    override fun clone() = throw NotImplementedError()
    override fun request() = throw NotImplementedError()
    override fun timeout() = throw NotImplementedError()
}

