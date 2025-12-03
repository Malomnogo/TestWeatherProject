package com.malomnogo.data.weather.cloud.model.location

import com.malomnogo.data.FakeProvideResources
import com.malomnogo.data.Order
import com.malomnogo.data.PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationCloudRemoteMapperTest {

    private lateinit var order: Order
    private lateinit var fakeProvideResources: FakeProvideResources
    private lateinit var mapper: LocationCloudRemoteMapper.ToDomain

    @Before
    fun setup() {
        order = Order()
        fakeProvideResources = FakeProvideResources(order)
        fakeProvideResources.serviceSentUnknownDataResult = "Unknown location"
        mapper = LocationCloudRemoteMapper.ToDomain(
            provideResources = fakeProvideResources
        )
    }

    @Test
    fun testMapSuccess() {
        val result = mapper.map(name = "Moscow")

        assertEquals("Moscow", result)
        assertEquals(Order(), order)
    }

    @Test
    fun testMapNull() {
        val result = mapper.map(name = null)

        assertEquals("Unknown location", result)
        assertEquals(
            Order(
                mutableListOf(PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA)
            ),
            order
        )
    }
}

