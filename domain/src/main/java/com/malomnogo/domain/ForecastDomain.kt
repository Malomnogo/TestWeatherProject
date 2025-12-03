package com.malomnogo.domain

interface ForecastDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(forecastDay: List<ForecastDayDomain>): T

        fun mapError(message: String): T
    }

    class Success(
        private val forecastDay: List<ForecastDayDomain>
    ) : ForecastDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            forecastDay = forecastDay
        )
    }

    class Error(
        private val message: String
    ) : ForecastDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}