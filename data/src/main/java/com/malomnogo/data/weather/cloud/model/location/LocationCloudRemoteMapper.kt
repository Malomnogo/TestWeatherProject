package com.malomnogo.data.weather.cloud.model.location

import com.malomnogo.data.core.ProvideResources

interface LocationCloudRemoteMapper<T> {

    fun map(name: String?): T

    class ToDomain(
        private val provideResources: ProvideResources
    ) : LocationCloudRemoteMapper<String> {

        override fun map(name: String?) = name ?: provideResources.serviceSentUnknownData()
    }
}