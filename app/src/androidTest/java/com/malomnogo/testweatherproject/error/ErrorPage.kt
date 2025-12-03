package com.malomnogo.testweatherproject.error

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.presentation.R
import com.malomnogo.testweatherproject.core.ColorMatcher
import com.malomnogo.presentation.core.views.BaseError
import com.malomnogo.presentation.core.views.ColorfulTextView
import com.malomnogo.presentation.weather.views.RetryButton
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class ErrorPage {

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.error),
                isAssignableFrom(BaseError::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
            )
        ).check(matches(not(isDisplayed())))
    }

    fun checkError(message: String) {
        onView(
            allOf(
                withId(R.id.errorTextView),
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(BaseError::class.java)),
            )
        )
            .check(matches(withText(message)))
            .check(matches(ColorMatcher("#FF0000")))
    }

    fun clickRetry() {
        onView(
            allOf(
                withId(R.id.retryButton),
                withText("Retry"),
                isAssignableFrom(Button::class.java),
                withParent(isAssignableFrom(BaseError::class.java)),
            )
        ).perform(click())
    }
}