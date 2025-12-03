package com.malomnogo.presentation.core

import androidx.lifecycle.ViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    @Suppress("UNCHECKED_CAST")
    class Factory(private val makeViewModel: ProvideViewModel) : ProvideViewModel {

        private val map = HashMap<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
            if (map.containsKey(viewModelClass)) {
                map[viewModelClass]
            } else {
                val viewModel = makeViewModel.viewModel(viewModelClass)
                map[viewModelClass] = viewModel
                viewModel
            } as T
    }
}