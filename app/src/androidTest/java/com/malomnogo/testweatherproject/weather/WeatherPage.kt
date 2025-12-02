package com.malomnogo.testweatherproject.weather

import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.presentation.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class WeatherPage {

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.weatherLayout),
                isAssignableFrom(LinearLayout::class.java),
            )
        ).check(matches(not(isDisplayed())))
    }

    fun checkCurrentWeather(city: String, temperature: String) {
        onView(
            allOf(
                withId(R.id.cityTextView),
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.weatherLayout))
            )
        ).check(matches(withText(city)))

        onView(
            allOf(
                withId(R.id.currentTemperatureTextView),
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(R.id.weatherLayout))
            )
        ).check(matches(withText(temperature)))
    }
}