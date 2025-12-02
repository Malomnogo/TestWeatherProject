package com.malomnogo.presentation.core

interface FormatWeather {

    fun formatWeather(temperature: Double): String

    class Base : FormatWeather {

        override fun formatWeather(temperature: Double): String {
            val formatted = if (temperature % 1.0 == 0.0)
                temperature.toInt().toString()
            else
                temperature.toString()

            return "$formatted°C"
        }
    }
}