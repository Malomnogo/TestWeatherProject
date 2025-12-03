package com.malomnogo.presentation.weather

interface ForecastDayUiState {

    data class Success(
        val dateEpoch: String,
        val day: DayUiState,
        val hour: List<HourUiState>
    ) : ForecastDayUiState

    data object Error : ForecastDayUiState
}
