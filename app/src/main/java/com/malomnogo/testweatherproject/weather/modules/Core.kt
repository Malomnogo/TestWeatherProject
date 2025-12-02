package com.malomnogo.testweatherproject.weather.modules

import com.malomnogo.testweatherproject.weather.presentation.core.RunAsync
import kotlinx.coroutines.Dispatchers

interface Core {

    fun provideRunAsync(): RunAsync

    class Base() : Core {

        private val runAsync =
            RunAsync.Base(dispatcherIO = Dispatchers.IO, dispatcherMain = Dispatchers.Main)

        override fun provideRunAsync() = runAsync
    }
}