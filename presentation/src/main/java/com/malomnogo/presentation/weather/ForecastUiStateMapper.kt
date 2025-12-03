package com.malomnogo.presentation.weather

import com.malomnogo.presentation.core.ProvideResources

class BaseForecastUiStateMapper(
    private val provideResources: ProvideResources,
    private val hourForecastTitle: String
) : ForecastUiState.Mapper<Pair<HourForecastUiState, DayForecastUiState>> {

    override fun mapSuccess(
        hourlyForecast: List<HourUiState>,
        dailyForecast: List<DayUiState>
    ) = Pair(
        HourForecastUiState.Base(
            title = hourForecastTitle,
            hourlyForecast = hourlyForecast
        ),
        DayForecastUiState.Base(
            dailyForecast = dailyForecast
        )
    )

    override fun mapError() = Pair(
        HourForecastUiState.Empty,
        DayForecastUiState.Empty
    )
}

