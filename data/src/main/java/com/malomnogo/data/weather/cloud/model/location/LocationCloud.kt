package com.malomnogo.data.weather.cloud.model.location

import com.google.gson.annotations.SerializedName

data class LocationCloud(
    @SerializedName("name")
    private val name: String?
) {

    fun <T : Any> map(mapper: LocationCloudRemoteMapper<T>) = mapper.map(name = name)
}