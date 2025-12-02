package com.malomnogo.testweatherproject.error

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.malomnogo.testweatherproject.core.ColorMatcher
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
                isAssignableFrom(ColorfulTextView::class.java),
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
                isAssignableFrom(RetryButton::class.java),
                withParent(isAssignableFrom(BaseError::class.java)),
            )
        ).perform(click())
    }
}