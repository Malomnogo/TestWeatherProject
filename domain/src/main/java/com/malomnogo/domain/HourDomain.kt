package com.malomnogo.domain

interface HourDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(
            timeEpoch: Long,
            tempC: Double
        ): T

        fun mapError(message: String): T
    }

    class Success(
        private val timeEpoch: Long,
        private val tempC: Double
    ) : HourDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            timeEpoch = timeEpoch,
            tempC = tempC
        )
    }

    class Error(
        private val message: String
    ) : HourDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}