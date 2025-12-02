package com.malomnogo.testweatherproject.weather

import android.content.Context
import com.malomnogo.data.core.ProvideResources
import com.malomnogo.presentation.R

class BaseProvideResources(
    private val context: Context
) : ProvideResources {

    override fun serviceSentUnknownData() =
        context.resources.getString(R.string.service_sent_unknown_data)

    override fun noInternetConnectionMessage() =
        context.resources.getString(R.string.no_internet_connection)

    override fun serviceUnavailableMessage() =
        context.resources.getString(R.string.service_unavailable)
}