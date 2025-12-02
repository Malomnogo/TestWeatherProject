package com.malomnogo.testweatherproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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

        progressPage.checkVisible()
        errorPage.checkNotVisible()
        weatherPage.checkNotVisible()

        errorPage.checkError("No internet connection")
        progressPage.checkNotVisible()
        weatherPage.checkNotVisible()

        errorPage.clickRetry()
        progressPage.checkVisible()
        errorPage.checkNotVisible()
        weatherPage.checkNotVisible()

        weatherPage.checkWeather(
            city = "Moscow",
            temperature = "30°C"
        )
        progressPage.checkNotVisbile()
        errorPage.checkNotVisible()
    }
}