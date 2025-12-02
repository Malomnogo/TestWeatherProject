package com.malomnogo.data.core

interface ProvideResources {

    fun serviceSentUnknownData(): String
    fun noInternetConnectionMessage(): String
    fun serviceUnavailableMessage(): String
}