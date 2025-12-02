package com.malomnogo.testweatherproject.weather

import androidx.lifecycle.ViewModel
import com.malomnogo.presentation.core.ProvideViewModel
import com.malomnogo.testweatherproject.weather.modules.ProvideModule

class BaseProvideViewModel(private val provideModule: ProvideModule) : ProvideViewModel {

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return provideModule.module(viewModelClass).viewModel()
    }
}