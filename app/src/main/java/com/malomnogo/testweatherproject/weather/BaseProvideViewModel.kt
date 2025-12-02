package com.malomnogo.testweatherproject.weather

import androidx.lifecycle.ViewModel
import com.malomnogo.testweatherproject.weather.modules.ProvideModule
import com.malomnogo.testweatherproject.weather.presentation.core.ProvideViewModel

class BaseProvideViewModel(private val provideModule: ProvideModule) : ProvideViewModel {

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return provideModule.module(viewModelClass).viewModel()
    }
}