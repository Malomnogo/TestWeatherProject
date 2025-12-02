package com.malomnogo.data.weather.cloud.model.core

import com.malomnogo.data.FakeProvideResources
import com.malomnogo.data.Order
import com.malomnogo.data.weather.cloud.model.location.LocationCloud
import com.malomnogo.data.weather.cloud.model.location.LocationCloudRemoteMapper
import com.malomnogo.data.weather.cloud.model.temperature.CurrentTemperatureCloud
import com.malomnogo.data.weather.cloud.model.temperature.TemperatureCloudMapper
import com.malomnogo.data.PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private const val LOCATION_CLOUD_REMOTE_MAPPER_MAP = "LocationCloudRemoteMapper#map"
private const val TEMPERATURE_CLOUD_REMOTE_MAPPER_MAP = "TemperatureCloudRemoteMapper#map"

class WeatherCloudRemoteMapperTest {

    private lateinit var order: Order
    private lateinit var fakeProvideResources: FakeProvideResources
    private lateinit var fakeLocationMapper: FakeLocationCloudRemoteMapper
    private lateinit var fakeTemperatureMapper: FakeTemperatureCloudMapper
    private lateinit var mapper: WeatherCloudMapper.ToDomain

    @Before
    fun setup() {
        order = Order()
        fakeProvideResources = FakeProvideResources(order)
        fakeProvideResources.serviceSentUnknownDataResult = "Unknown data"
        fakeLocationMapper = FakeLocationCloudRemoteMapper(order)
        fakeTemperatureMapper = FakeTemperatureCloudMapper(order)
        mapper = WeatherCloudMapper.ToDomain(
            locationRemoteMapper = fakeLocationMapper,
            temperatureCloudMapper = fakeTemperatureMapper,
            provideResources = fakeProvideResources
        )
    }

    @Test
    fun testMapSuccess() {
        fakeLocationMapper.result = "Moscow"
        fakeTemperatureMapper.result = TemperatureDomain.Success(temperature = 30.0)

        val result = mapper.map(
            location = LocationCloud(name = "Moscow"),
            current = CurrentTemperatureCloud(tempC = 30.0)
        )

        assert(result is WeatherDomain.Success)
        val successResult = result as WeatherDomain.Success
        val mapped = successResult.map(object : WeatherDomain.Mapper<Pair<String, Double>> {
            override fun mapSuccess(city: String, temperature: TemperatureDomain): Pair<String, Double> {
                val temp = temperature.map(object : TemperatureDomain.Mapper<Double> {
                    override fun mapSuccess(temperature: Double) = temperature
                    override fun mapError(message: String) = -1.0
                })
                return Pair(city, temp)
            }
            override fun mapError(message: String) = Pair("", -1.0)
        })
        assertEquals("Moscow", mapped.first)
        assertEquals(30.0, mapped.second, 0.0)
        assertEquals(
            Order(
                mutableListOf(
                    LOCATION_CLOUD_REMOTE_MAPPER_MAP,
                    TEMPERATURE_CLOUD_REMOTE_MAPPER_MAP
                )
            ),
            order
        )
    }

    @Test
    fun testMapNullLocation() {
        fakeTemperatureMapper.result = TemperatureDomain.Success(temperature = 30.0)
        val result = mapper.map(
            location = null,
            current = CurrentTemperatureCloud(tempC = 30.0)
        )

        assertEquals(
            WeatherDomain.Error(message = "Unknown data"),
            result
        )
        assertEquals(
            Order(
                mutableListOf(
                    TEMPERATURE_CLOUD_REMOTE_MAPPER_MAP,
                    PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA
                )
            ),
            order
        )
    }

    @Test
    fun testMapNullCurrent() {
        fakeLocationMapper.result = "Moscow"
        val result = mapper.map(
            location = LocationCloud(name = "Moscow"),
            current = null
        )

        assertEquals(
            WeatherDomain.Error(message = "Unknown data"),
            result
        )
        assertEquals(
            Order(
                mutableListOf(
                    LOCATION_CLOUD_REMOTE_MAPPER_MAP,
                    PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA
                )
            ),
            order
        )
    }

    @Test
    fun testMapBothNull() {
        val result = mapper.map(
            location = null,
            current = null
        )

        assertEquals(
            WeatherDomain.Error(message = "Unknown data"),
            result
        )
        assertEquals(
            Order(
                mutableListOf(PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA)
            ),
            order
        )
    }
}

private class FakeLocationCloudRemoteMapper(
    private val order: Order
) : LocationCloudRemoteMapper<String> {

    var result: String = ""

    override fun map(name: String?): String {
        order.add(LOCATION_CLOUD_REMOTE_MAPPER_MAP)
        return result
    }
}

private class FakeTemperatureCloudMapper(
    private val order: Order
) : TemperatureCloudMapper<TemperatureDomain> {

    var result: TemperatureDomain = TemperatureDomain.Error(message = "")

    override fun map(temperature: Double?): TemperatureDomain {
        order.add(TEMPERATURE_CLOUD_REMOTE_MAPPER_MAP)
        return result
    }
}

