package com.malomnogo.testweatherproject.weather.modules

import androidx.lifecycle.ViewModel
import com.malomnogo.presentation.weather.WeatherViewModel
import com.malomnogo.testweatherproject.weather.ProvideInstance

interface ProvideModule {

    fun <T : ViewModel> module(clazz: Class<T>): Module<T>

    @Suppress("UNCHECKED_CAST")
    class Base(
        private val core: Core,
        private val provideInstance: ProvideInstance
    ) : ProvideModule {

        override fun <T : ViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                WeatherViewModel::class.java -> WeatherModule(
                    core = core,
                    provideInstance = provideInstance
                )
                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}