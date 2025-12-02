package com.malomnogo.testweatherproject.weather.presentation.core

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.R as MaterialR
import com.malomnogo.testweatherproject.R

class WeatherLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), ShowWeather {

    private val cityTextView: TextView
    private val currentTemperatureTextView: TextView

    init {
        id = R.id.weatherLayout
        orientation = VERTICAL
        gravity = Gravity.CENTER
        val padding = resources.getDimensionPixelSize(R.dimen.big_padding)
        setPadding(padding, padding, padding, padding)

        cityTextView = AppCompatTextView(context).apply {
            id = R.id.cityTextView
            TextViewCompat.setTextAppearance(
                this,
                MaterialR.style.TextAppearance_MaterialComponents_Headline6
            )
            setTextColor(ContextCompat.getColor(context, R.color.black))
            gravity = Gravity.CENTER
        }

        currentTemperatureTextView = AppCompatTextView(context).apply {
            id = R.id.currentTemperatureTextView
            TextViewCompat.setTextAppearance(
                this,
                MaterialR.style.TextAppearance_MaterialComponents_Headline3
            )
            setTextColor(ContextCompat.getColor(context, R.color.black))
            gravity = Gravity.CENTER
        }

        addView(cityTextView)
        addView(currentTemperatureTextView)
    }

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val visibilityState = VisibilityState(it)
        visibilityState.save(this)
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as VisibilityState?
        super.onRestoreInstanceState(savedState?.superState)
        savedState?.restore(this)
    }

    override fun change(visible: Boolean) {
        this.visibility = if (visible) VISIBLE else GONE
    }

    override fun showCity(city: String) {
        cityTextView.text = city
    }

    override fun showTemperature(temperature: String) {
        currentTemperatureTextView.text = temperature
    }
}

