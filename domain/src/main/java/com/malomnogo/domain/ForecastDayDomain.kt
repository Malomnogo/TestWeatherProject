package com.malomnogo.domain

interface ForecastDayDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(
            dateEpoch: Long,
            day: DayDomain,
            hour: List<HourDomain>
        ): T

        fun mapError(message: String): T
    }

    class Success(
        private val dateEpoch: Long,
        private val day: DayDomain,
        private val hour: List<HourDomain>
    ) : ForecastDayDomain {
        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            dateEpoch = dateEpoch,
            day = day,
            hour = hour
        )
    }

    class Error(
        private val message: String
    ) : ForecastDayDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}