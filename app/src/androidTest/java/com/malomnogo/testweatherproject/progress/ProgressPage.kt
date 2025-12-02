package com.malomnogo.testweatherproject.progress

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class ProgressPage {

    fun checkVisible() {
        onView(
            allOf(
                withId(R.id.progressBar),
                isAssignableFrom(CustomProgressBar::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
            )
        ).check(matches(isDisplayed()))
    }

    fun checkNotVisible() {
        onView(
            allOf(
                withId(R.id.progressBar),
                isAssignableFrom(CustomProgressBar::class.java),
                withParent(isAssignableFrom(LinearLayout::class.java)),
            )
        ).check(matches(not(isDisplayed())))
    }
}