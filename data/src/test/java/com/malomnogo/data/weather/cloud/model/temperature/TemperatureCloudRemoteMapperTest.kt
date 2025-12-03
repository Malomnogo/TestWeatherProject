package com.malomnogo.data.weather.cloud.model.temperature

import com.malomnogo.data.FakeProvideResources
import com.malomnogo.data.Order
import com.malomnogo.data.PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA
import com.malomnogo.domain.TemperatureDomain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TemperatureCloudRemoteMapperTest {

    private lateinit var order: Order
    private lateinit var fakeProvideResources: FakeProvideResources
    private lateinit var mapper: TemperatureCloudMapper.ToDomain

    @Before
    fun setup() {
        order = Order()
        fakeProvideResources = FakeProvideResources(order)
        fakeProvideResources.serviceSentUnknownDataResult = "Unknown temperature"
        mapper = TemperatureCloudMapper.ToDomain(
            provideResources = fakeProvideResources,
            conditionMapper = FakeTemperatureConditionCloudMapper(order)
        )
    }

    @Test
    fun testMapSuccess() {
        val result = mapper.map(
            temperature = 30.0,
            condition = com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloud(
                text = "Sunny",
                icon = "//icon.png"
            )
        )

        assert(result is TemperatureDomain.Success)
        val successResult = result as TemperatureDomain.Success
        val mapped = successResult.map(object : TemperatureDomain.Mapper<Double> {
            override fun mapSuccess(temperature: Double, condition: com.malomnogo.domain.ConditionDomain) = temperature
            override fun mapError(message: String) = -1.0
        })
        assertEquals(30.0, mapped, 0.0)
        assertEquals(Order(), order)
    }

    @Test
    fun testMapNull() {
        val result = mapper.map(
            temperature = null,
            condition = null
        )

        assert(result is TemperatureDomain.Error)
        val errorResult = result as TemperatureDomain.Error
        val mapped = errorResult.map(object : TemperatureDomain.Mapper<String> {
            override fun mapSuccess(temperature: Double, condition: com.malomnogo.domain.ConditionDomain) = ""
            override fun mapError(message: String) = message
        })
        assertEquals("Unknown temperature", mapped)
        assertEquals(
            Order(
                mutableListOf(PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA)
            ),
            order
        )
    }
}

private class FakeTemperatureConditionCloudMapper(
    private val order: Order
) : com.malomnogo.data.weather.cloud.model.condition.TemperatureConditionCloudMapper<com.malomnogo.domain.ConditionDomain> {

    var result: com.malomnogo.domain.ConditionDomain = com.malomnogo.domain.ConditionDomain.Success(
        text = "Sunny",
        iconUrl = "http://icon.png"
    )

    override fun map(
        text: String?,
        icon: String?
    ): com.malomnogo.domain.ConditionDomain {
        return result
    }
}

