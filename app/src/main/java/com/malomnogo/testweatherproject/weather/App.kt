package com.malomnogo.testweatherproject.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import com.malomnogo.presentation.core.LoadPicEngine
import com.malomnogo.presentation.core.ProvideLoadPicEngine
import com.malomnogo.presentation.core.ProvideViewModel
import com.malomnogo.testweatherproject.weather.modules.Core
import com.malomnogo.testweatherproject.weather.modules.ProvideModule

abstract class App : Application(), ProvideViewModel, ProvideLoadPicEngine {

    private lateinit var factory: ProvideViewModel.Factory

    override fun onCreate() {
        super.onCreate()
        factory = ProvideViewModel.Factory(
            makeViewModel = BaseProvideViewModel(
                ProvideModule.Base(
                    core = Core.Base(context = this),
                    provideInstance = provideInstance()
                )
            )
        )
    }

    abstract fun provideInstance(): ProvideInstance

    abstract override fun picEngine(): LoadPicEngine

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        factory.viewModel(viewModelClass)
}

class Release : App() {

    override fun provideInstance() = ProvideInstance.Base()

    override fun picEngine() = BaseLoadPicEngine()
}

class Mock : App() {

    override fun provideInstance() = ProvideInstance.Mock()

    override fun picEngine() = MockLoadPicEngine()
}