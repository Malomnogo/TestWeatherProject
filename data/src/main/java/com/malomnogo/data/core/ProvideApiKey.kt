package com.malomnogo.data.core

interface ApiKeyStorage {

    fun weatherApiKey(): String

    class Base(
        private val apiKey: String
    ) : ApiKeyStorage {
        
        override fun weatherApiKey(): String {
            return apiKey
        }
    }
}