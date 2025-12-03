package com.malomnogo.testweatherproject.weather.modules

import android.content.Context
import com.malomnogo.data.core.HandleError
import com.malomnogo.data.core.ProvideResources
import com.malomnogo.presentation.core.RunAsync
import com.malomnogo.testweatherproject.weather.BaseProvideResources
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Core {

    fun provideRunAsync(): RunAsync

    fun provideResources(): ProvideResources

    fun provideHandleError(): HandleError

    fun provideRetrofit(): Retrofit

    class Base(context: Context) : Core {

        private val provideResources by lazy { BaseProvideResources(context = context) }

        private val handleError by lazy { HandleError.Base(provideResources = provideResources) }

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                ).build()
        }

        private val runAsync =
            RunAsync.Base(dispatcherIO = Dispatchers.IO, dispatcherMain = Dispatchers.Main)

        override fun provideRunAsync() = runAsync

        override fun provideRetrofit(): Retrofit = retrofit

        override fun provideResources() = provideResources

        override fun provideHandleError() = handleError
    }
}