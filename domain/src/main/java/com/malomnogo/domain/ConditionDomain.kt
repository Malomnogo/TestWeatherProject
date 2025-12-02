package com.malomnogo.domain

interface ConditionDomain {

    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        fun mapSuccess(
            text: String,
            iconUrl: String
        ): T

        fun mapError(message: String): T
    }

    class Success(
        private val text: String,
        private val iconUrl: String
    ) : ConditionDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapSuccess(
            text = text,
            iconUrl = iconUrl
        )
    }

    class Error(
        private val message: String
    ) : ConditionDomain {

        override fun <T : Any> map(mapper: Mapper<T>) = mapper.mapError(message = message)
    }
}