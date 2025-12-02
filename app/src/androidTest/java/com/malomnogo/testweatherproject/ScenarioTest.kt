package com.malomnogo.testweatherproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.malomnogo.presentation.MainActivity
import com.malomnogo.testweatherproject.error.ErrorPage
import com.malomnogo.testweatherproject.progress.ProgressPage
import com.malomnogo.testweatherproject.weather.WeatherPage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    /**
     * ⚠️ Before running the tests, you must enable the mock Application.
     * Open `AndroidManifest.xml` and replace:
     * `android:name=".weather.Release"`
     * with
     * `android:name=".weather.Mock"`
     * Without this change the tests will not run correctly.
     */

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

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