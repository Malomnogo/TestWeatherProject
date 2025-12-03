package com.malomnogo.presentation.weather

interface ForecastUiState {

    val hourlyForecast: List<HourUiState>
    val dailyForecast: List<DayUiState>

    data object Empty : ForecastUiState {
        override val hourlyForecast = emptyList<HourUiState>()
        override val dailyForecast = emptyList<DayUiState>()
    }

    data class Base(
        override val hourlyForecast: List<HourUiState>,
        override val dailyForecast: List<DayUiState>
    ) : ForecastUiState
}
