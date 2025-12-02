package ru.easycode.waifupics.core.modules

import ru.easycode.presentation.favorite.FavoritesViewModel
import ru.easycode.presentation.main.Clear
import ru.easycode.presentation.main.CustomViewModel
import ru.easycode.presentation.main.MainViewModel
import ru.easycode.presentation.pics.NewPicsViewModel
import ru.easycode.presentation.settings.category.presentation.ChooseCategoryViewModel
import ru.easycode.presentation.settings.type.presentation.ChooseTypeViewModel
import ru.easycode.waifupics.core.Core
import ru.easycode.waifupics.core.ProvideInstance

interface ProvideModule {

    fun <T : CustomViewModel> module(clazz: Class<T>): Module<T>

    class Base(
        private val provideInstance: ProvideInstance,
        private val clear: Clear,
        private val core: Core
    ) : ProvideModule {

        override fun <T : CustomViewModel> module(clazz: Class<T>): Module<T> {
            return when (clazz) {
                MainViewModel::class.java -> MainModule(core, clear)
                ChooseTypeViewModel::class.java -> ChooseTypeModule(core, clear)
                ChooseCategoryViewModel::class.java -> ChooseCategoryModule(core, clear)
                NewPicsViewModel::class.java -> NewPicsModule(core, provideInstance)
                FavoritesViewModel::class.java -> FavoritesModule(core)
                else -> throw IllegalStateException("unknown viewModel $clazz")
            } as Module<T>
        }
    }
}