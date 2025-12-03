package com.malomnogo.presentation.weather.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R as MaterialR
import com.malomnogo.presentation.R
import com.malomnogo.presentation.weather.TemperatureUiState
import com.malomnogo.presentation.core.views.CustomImageView
import com.malomnogo.presentation.core.views.VisibilityState
import com.malomnogo.presentation.day.DayForecastAdapter
import com.malomnogo.presentation.day.DayForecastUiState
import com.malomnogo.presentation.day.ShowDayForecast
import com.malomnogo.presentation.hour.HourForecastAdapter
import com.malomnogo.presentation.hour.HourUiState
import com.malomnogo.presentation.hour.ShowHourForecast
import com.malomnogo.presentation.weather.ForecastUiState

class WeatherLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ScrollView(context, attrs, defStyleAttr), ShowWeather {

    private val contentLayout: LinearLayout
    private val cityTextView: TextView
    private val currentTemperatureTextView: TextView
    private val currentWeatherIcon: CustomImageView
    private val hourForecastTitleTextView: TextView
    private val hourlyForecastRecyclerView: RecyclerView
    private val dayForecastTitleTextView: TextView
    private val dailyForecastRecyclerView: RecyclerView
    private val hourForecastAdapter: HourForecastAdapter
    private val dayForecastAdapter: DayForecastAdapter
    private val temperatureView: ShowTemperature

    init {
        id = R.id.weatherLayout

        contentLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            val padding = resources.getDimensionPixelSize(R.dimen.big_padding)
            setPadding(padding, padding, padding, padding)
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }

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

        currentWeatherIcon = CustomImageView(context).apply {
            id = R.id.currentWeatherIcon
            layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.icon_size),
                resources.getDimensionPixelSize(R.dimen.icon_size)
            ).apply {
                gravity = Gravity.CENTER
                val margin = resources.getDimensionPixelSize(R.dimen.small_padding)
                setMargins(0, margin, 0, margin)
            }
        }

        temperatureView = object : ShowTemperature {
            override fun showTemperature(temperature: String) {
                currentTemperatureTextView.text = temperature
            }

            override fun showIcon(iconUrl: String) {
                currentWeatherIcon.show(iconUrl)
            }

            override fun change(visible: Boolean) {
                currentTemperatureTextView.visibility = if (visible) VISIBLE else GONE
                currentWeatherIcon.visibility = if (visible) VISIBLE else GONE
            }
        }

        hourForecastTitleTextView = AppCompatTextView(context).apply {
            id = R.id.hourForecastTitleTextView
            TextViewCompat.setTextAppearance(
                this,
                MaterialR.style.TextAppearance_MaterialComponents_Headline6
            )
            setTextColor(ContextCompat.getColor(context, R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                val margin = resources.getDimensionPixelSize(R.dimen.big_padding)
                setMargins(margin, margin, margin, 0)
            }
        }

        hourForecastAdapter = HourForecastAdapter()
        hourlyForecastRecyclerView = RecyclerView(context).apply {
            id = R.id.hourlyForecastRecyclerView
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hourForecastAdapter
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                val margin = resources.getDimensionPixelSize(R.dimen.big_padding)
                setMargins(0, 0, 0, margin)
            }
        }

        dayForecastTitleTextView = AppCompatTextView(context).apply {
            id = R.id.dayForecastTitleTextView
            TextViewCompat.setTextAppearance(
                this,
                MaterialR.style.TextAppearance_MaterialComponents_Headline6
            )
            setTextColor(ContextCompat.getColor(context, R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                val margin = resources.getDimensionPixelSize(R.dimen.big_padding)
                setMargins(margin, margin, margin, 0)
            }
        }

        dayForecastAdapter = DayForecastAdapter()
        dailyForecastRecyclerView = RecyclerView(context).apply {
            id = R.id.dailyForecastRecyclerView
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = dayForecastAdapter
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                val margin = resources.getDimensionPixelSize(R.dimen.big_padding)
                setMargins(0, 0, 0, margin)
            }
            addItemDecoration(DayForecastItemDecoration(context))
        }

        contentLayout.addView(cityTextView)
        contentLayout.addView(currentTemperatureTextView)
        contentLayout.addView(currentWeatherIcon)
        contentLayout.addView(hourForecastTitleTextView)
        contentLayout.addView(hourlyForecastRecyclerView)
        contentLayout.addView(dayForecastTitleTextView)
        contentLayout.addView(dailyForecastRecyclerView)

        addView(contentLayout)
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

    override fun showTemperature(temperature: TemperatureUiState) {
        temperature.update(temperatureView)
    }

    override fun showHourForecast(hourForecast: ForecastUiState.Hour) {
        hourForecast.update(object : ShowHourForecast {

            override fun showHourlyForecast(hourlyForecast: List<HourUiState>) {
                hourForecastAdapter.update(hourlyForecast)
            }

            override fun change(visible: Boolean) {
                if (visible) {
                    hourForecastTitleTextView.text = context.getString(R.string.forecast_by_hour)
                }
                hourForecastTitleTextView.visibility = if (visible) VISIBLE else GONE
                hourlyForecastRecyclerView.visibility = if (visible) VISIBLE else GONE
            }
        })
    }

    override fun showDayForecast(dayForecast: ForecastUiState.Day) {
        dayForecast.update(object : ShowDayForecast {

            override fun showDailyForecast(dailyForecast: List<DayForecastUiState>) {
                dayForecastAdapter.update(dailyForecast)
            }

            override fun change(visible: Boolean) {
                if (visible) {
                    dayForecastTitleTextView.text = context.getString(R.string.forecast_by_3_days)
                }
                dayForecastTitleTextView.visibility = if (visible) VISIBLE else GONE
                dailyForecastRecyclerView.visibility = if (visible) VISIBLE else GONE
            }
        })
    }

    private class DayForecastItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

        private val paint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.light_gray)
            strokeWidth = 4f
            isAntiAlias = true
        }

        private val separatorWidth = context.resources.getDimensionPixelSize(R.dimen.small_padding)
        private val iconSize = context.resources.getDimensionPixelSize(R.dimen.icon_size)

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION && position < state.itemCount - 1) {
                outRect.right = separatorWidth
            }
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            if (parent.layoutManager !is LinearLayoutManager) return

            val childCount = parent.childCount

            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val position = parent.getChildAdapterPosition(child)

                if (position != RecyclerView.NO_POSITION && position < state.itemCount - 1) {
                    val right = child.right.toFloat()
                    val centerY = child.top + child.height / 2f
                    val top = centerY - iconSize / 2f
                    val bottom = centerY + iconSize / 2f
                    c.drawLine(right, top, right, bottom, paint)
                }
            }
        }
    }
}