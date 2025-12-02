package com.malomnogo.domain

interface WeatherDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(
            city: String,
            temperature: Double
        ): T

        fun mapError(message: String): T
    }

    data class Success(
        private val city: String,
        private val temperature: Double
    ) : WeatherDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            city = city,
            temperature = temperature
        )
    }

    data class Error(
        private val message: String
    ) : WeatherDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}