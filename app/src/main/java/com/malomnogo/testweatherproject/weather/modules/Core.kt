package ru.easycode.waifupics.core

import android.content.Context
import ru.easycode.core.LocalStorage
import ru.easycode.core.ProvideResources
import ru.easycode.pics.cache.PicCacheDataSource
import ru.easycode.presentation.main.MainUiObservable
import ru.easycode.presentation.main.Navigation
import ru.easycode.presentation.main.RunAsync
import ru.easycode.settings.ChooseCategoryCache
import ru.easycode.settings.ChooseTypeCache
import ru.easycode.waifupics.core.data.BaseLocalStorage

interface Core {

    fun provideNavigation(): Navigation

    fun provideLocalStorage(): LocalStorage.Mutable

    fun provideChooseCategoryCache(): ChooseCategoryCache.Mutable

    fun provideChooseTypeCache(): ChooseTypeCache.Mutable

    fun provideMainUiObservable(): MainUiObservable.Mutable

    fun provideResources(): ProvideResources

    fun provideRunAsync(): RunAsync

    fun providePicCacheDataSource(): PicCacheDataSource.Mutable

    class Base(private val context: Context) : Core {

        private val localStorage: LocalStorage.Mutable by lazy {
            BaseLocalStorage(context = context)
        }
        private val chooseTypeCache: ChooseTypeCache.Mutable by lazy {
            ChooseTypeCache.Base(localStorage = localStorage)
        }
        private val chooseCategoryCache: ChooseCategoryCache.Mutable by lazy {
            ChooseCategoryCache.Base(localStorage = localStorage)
        }
        private val picCacheDataSource by lazy {
            PicCacheDataSource.Base(
                context = context,
                typeCache = chooseTypeCache,
                categoryCache = chooseCategoryCache
            )
        }
        private val navigation = Navigation.Base()
        private val mainUiObservable = MainUiObservable.Base()
        private val provideResources = BaseProvideResources(context = context)
        private val runAsync = RunAsync.Base()

        override fun provideNavigation() = navigation

        override fun provideLocalStorage() = localStorage

        override fun provideChooseCategoryCache() = chooseCategoryCache

        override fun provideChooseTypeCache() = chooseTypeCache

        override fun provideMainUiObservable() = mainUiObservable

        override fun provideResources() = provideResources

        override fun provideRunAsync() = runAsync

        override fun providePicCacheDataSource() = picCacheDataSource
    }
}