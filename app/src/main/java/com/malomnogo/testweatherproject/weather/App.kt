package com.malomnogo.testweatherproject.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import com.malomnogo.testweatherproject.weather.modules.Core
import com.malomnogo.testweatherproject.weather.modules.ProvideModule
import com.malomnogo.testweatherproject.weather.presentation.core.ProvideViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        factory = ProvideViewModel.Factory(
            makeViewModel = BaseProvideViewModel(
                ProvideModule.Base(core = Core.Base())
            )
        )
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)
}