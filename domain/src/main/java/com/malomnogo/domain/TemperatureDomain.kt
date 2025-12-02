package com.malomnogo.domain

interface TemperatureDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(temperature: Double): T
        fun mapError(message: String): T
    }

    class Success(
        private val temperature: Double
    ) : TemperatureDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(temperature = temperature)
    }

    class Error(
        private val message: String
    ) : TemperatureDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}