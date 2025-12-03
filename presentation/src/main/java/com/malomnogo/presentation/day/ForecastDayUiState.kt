package com.malomnogo.presentation.day

import com.malomnogo.presentation.hour.HourUiState
import com.malomnogo.presentation.weather.views.ShowDay
import com.malomnogo.presentation.hour.ShowHourForecast

interface ForecastDayUiState {

    fun update(dayView: ShowDay)

    fun updateHour(hourView: ShowHourForecast)

    fun toDayForecastUiState(): DayForecastUiState

    data class Success(
        val dateEpoch: String,
        val day: DayUiState,
        val hour: List<HourUiState>
    ) : ForecastDayUiState {

        override fun update(dayView: ShowDay) {
            dayView.showDate(dateEpoch)
            day.update(dayView)
            dayView.change(true)
        }

        override fun updateHour(hourView: ShowHourForecast) {
            hourView.showHourlyForecast(hour)
            hourView.change(true)
        }

        override fun toDayForecastUiState() = DayForecastUiState.Base(
            date = dateEpoch,
            temperature = day
        )
    }

    data object Error : ForecastDayUiState {
        override fun update(dayView: ShowDay) {
            dayView.change(false)
        }

        override fun updateHour(hourView: ShowHourForecast) {
            hourView.change(false)
        }

        override fun toDayForecastUiState() = DayForecastUiState.Empty
    }
}