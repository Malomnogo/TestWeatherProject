package com.malomnogo.domain

interface DayDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(
            maxTempC: Double,
            minTempC: Double,
            conditionDomain: ConditionDomain
        ): T

        fun mapError(message: String): T
    }

    class Success(
        private val maxTempC: Double,
        private val minTempC: Double,
        private val conditionDomain: ConditionDomain
    ) : DayDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            maxTempC = maxTempC,
            minTempC = minTempC,
            conditionDomain = conditionDomain
        )
    }

    class Error(
        private val message: String
    ) : DayDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}