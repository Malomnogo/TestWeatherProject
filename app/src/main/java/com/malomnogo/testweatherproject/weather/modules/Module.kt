package ru.easycode.waifupics.core.modules

import ru.easycode.presentation.main.CustomViewModel

interface Module<T : CustomViewModel> {

    fun viewModel(): T
}