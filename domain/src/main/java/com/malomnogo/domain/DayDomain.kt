package com.malomnogo.domain

interface DayDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(
            maxTempC: Double,
            minTempC: Double,
            condition: ConditionDomain
        ): T

        fun mapError(message: String): T
    }

    class Success(
        private val maxTempC: Double,
        private val minTempC: Double,
        private val condition: ConditionDomain
    ) : DayDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            maxTempC = maxTempC,
            minTempC = minTempC,
            condition = condition
        )
    }

    class Error(
        private val message: String
    ) : DayDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}