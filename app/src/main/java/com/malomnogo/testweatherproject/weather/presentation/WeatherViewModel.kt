package com.malomnogo.testweatherproject.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malomnogo.testweatherproject.weather.presentation.core.RunAsync
import com.malomnogo.testweatherproject.weather.domain.WeatherDomain
import com.malomnogo.testweatherproject.weather.domain.WeatherRepository

class WeatherViewModel(
    private val runAsync: RunAsync,
    private val uiObservable: UiObservable,
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

    fun startGettingUpdates(uiCallBack: UpdateUi) {
        uiObservable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        uiObservable.updateObserver(UpdateUi.Empty)
    }
}