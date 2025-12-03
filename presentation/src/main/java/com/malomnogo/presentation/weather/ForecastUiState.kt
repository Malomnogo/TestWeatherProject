package com.malomnogo.presentation.weather

import com.malomnogo.presentation.day.ForecastDayUiState
import com.malomnogo.presentation.day.ShowDayForecast
import com.malomnogo.presentation.hour.ShowHourForecast
import com.malomnogo.presentation.weather.views.ShowWeather

interface ForecastUiState {

    fun update(weatherView: ShowWeather)

    data object Empty : ForecastUiState {

        override fun update(weatherView: ShowWeather) {
            weatherView.showHourForecast(Hour.Empty)
            weatherView.showDayForecast(Day.Empty)
        }
    }

    data class Base(
        private val forecastDay: List<ForecastDayUiState>
    ) : ForecastUiState {

        override fun update(weatherView: ShowWeather) {
            val firstDay = forecastDay.firstOrNull()
            weatherView.showHourForecast(
                if (firstDay != null) {
                    Hour.Base(forecastDay = firstDay)
                } else {
                    Hour.Empty
                }
            )
            weatherView.showDayForecast(
                Day.Base(forecastDay = forecastDay)
            )
        }
    }

    interface Hour {

        fun update(hourForecastView: ShowHourForecast)

        data object Empty : Hour {

            override fun update(hourForecastView: ShowHourForecast) {
                hourForecastView.change(false)
            }
        }

        data class Base(
            private val forecastDay: ForecastDayUiState
        ) : Hour {

            override fun update(hourForecastView: ShowHourForecast) {
                forecastDay.updateHour(hourForecastView)
            }
        }
    }

    interface Day {

        fun update(dayForecastView: ShowDayForecast)

        data object Empty : Day {

            override fun update(dayForecastView: ShowDayForecast) {
                dayForecastView.change(false)
            }
        }

        data class Base(
            private val forecastDay: List<ForecastDayUiState>
        ) : Day {

            override fun update(dayForecastView: ShowDayForecast) {
                dayForecastView.showDailyForecast(
                    forecastDay.map { it.toDayForecastUiState() }
                )
                dayForecastView.change(true)
            }
        }
    }
}