package com.malomnogo.data

import com.malomnogo.data.core.ProvideResources

internal const val PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA = "ProvideResources#serviceSentUnknownData"
private const val PROVIDE_RESOURCES_NO_INTERNET_CONNECTION_MESSAGE = "ProvideResources#noInternetConnectionMessage"
private const val PROVIDE_RESOURCES_SERVICE_UNAVAILABLE_MESSAGE = "ProvideResources#serviceUnavailableMessage"

class FakeProvideResources(
    private val order: Order
) : ProvideResources {

    var serviceSentUnknownDataResult: String = "Unknown data"
    var noInternetConnectionMessageResult: String = "No internet connection"
    var serviceUnavailableMessageResult: String = "Service unavailable"

    override fun serviceSentUnknownData(): String {
        order.add(PROVIDE_RESOURCES_SERVICE_SENT_UNKNOWN_DATA)
        return serviceSentUnknownDataResult
    }

    override fun noInternetConnectionMessage(): String {
        order.add(PROVIDE_RESOURCES_NO_INTERNET_CONNECTION_MESSAGE)
        return noInternetConnectionMessageResult
    }

    override fun serviceUnavailableMessage(): String {
        order.add(PROVIDE_RESOURCES_SERVICE_UNAVAILABLE_MESSAGE)
        return serviceUnavailableMessageResult
    }
}

