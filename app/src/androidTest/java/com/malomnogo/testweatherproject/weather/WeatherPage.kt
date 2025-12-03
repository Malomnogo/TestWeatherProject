package com.malomnogo.testweatherproject.weather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.presentation.R
import com.malomnogo.testweatherproject.core.RecyclerViewMatcher
import org.hamcrest.CoreMatchers.allOf

class WeatherPage {

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.weatherLayout),
                isAssignableFrom(android.widget.ScrollView::class.java)
            )
        ).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    fun checkCurrentWeather(city: String, temperature: String) {
        onView(
            allOf(
                withId(R.id.cityTextView),
                isDescendantOfA(withId(R.id.weatherLayout))
            )
        ).check(matches(withText(city)))

        onView(
            allOf(
                withId(R.id.currentTemperatureTextView),
                isDescendantOfA(withId(R.id.weatherLayout))
            )
        ).check(matches(withText(temperature)))
    }

    fun checkHourWeather(
        position: Int,
        time: String,
        temperature: String
    ) {
        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.hourTimeTextView,
                recyclerViewId = R.id.hourlyForecastRecyclerView
            )
        ).check(matches(withText(time)))

        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.hourTemperatureTextView,
                recyclerViewId = R.id.hourlyForecastRecyclerView
            )
        ).check(matches(withText(temperature)))
    }

    fun checkDayWeather(
        position: Int,
        date: String,
        minTemperature: String,
        maxTemperature: String
    ) {
        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.dayDateTextView,
                recyclerViewId = R.id.dailyForecastRecyclerView
            )
        ).check(matches(withText(date)))

        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.dayMinTemperatureTextView,
                recyclerViewId = R.id.dailyForecastRecyclerView
            )
        ).check(matches(withText(minTemperature)))

        onView(
            RecyclerViewMatcher(
                position = position,
                targetViewId = R.id.dayMaxTemperatureTextView,
                recyclerViewId = R.id.dailyForecastRecyclerView
            )
        ).check(matches(withText(maxTemperature)))
    }
}