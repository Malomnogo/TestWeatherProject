package com.malomnogo.testweatherproject.weather.modules

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel> {

    fun viewModel(): T
}