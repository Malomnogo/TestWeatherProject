package com.malomnogo.presentation

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.malomnogo.presentation.error.ErrorPage
import com.malomnogo.presentation.progress.ProgressPage
import com.malomnogo.presentation.weather.WeatherPage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun scenarioTest() {
        val weatherPage = WeatherPage()
        val progressPage = ProgressPage()
        val errorPage = ErrorPage()

        errorPage.checkError(message = "No internet connection")
        progressPage.checkNotVisible()
        weatherPage.checkNotVisible()

        errorPage.clickRetry()
        errorPage.checkNotVisible()

        weatherPage.checkCurrentWeather(
            city = "Moscow",
            temperature = "30°C"
        )
        progressPage.checkNotVisible()
        errorPage.checkNotVisible()
    }
}

