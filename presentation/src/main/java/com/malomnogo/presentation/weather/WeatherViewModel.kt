package com.malomnogo.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malomnogo.domain.WeatherDomain
import com.malomnogo.domain.WeatherRepository
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.presentation.weather.WeatherUiObservable
import com.malomnogo.presentation.weather.UpdateWeatherUi

class WeatherViewModel(
    private val runAsync: RunAsync,
    private val uiObservable: WeatherUiObservable,
    private val repository: WeatherRepository,
    private val mapper: WeatherDomain.Mapper<WeatherUiState>
) : ViewModel() {

    fun init(isFirstOpen: Boolean) {
        if (isFirstOpen) load()
    }

    fun load() {
        uiObservable.updateUi(WeatherUiState.Progress)
        runAsync.start(
            coroutineScope = viewModelScope,
            background = { repository.loadData() },
            uiBlock = { uiObservable.updateUi(it.map(mapper)) }
        )
    }

    fun startGettingUpdates(uiCallBack: UpdateWeatherUi) {
        uiObservable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UpdateWeatherUi.Empty)
    }
}