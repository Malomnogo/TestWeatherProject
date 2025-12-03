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

        activityScenarioRule.scenario.recreate()

        errorPage.checkError(message = "No internet connection")
        progressPage.checkNotVisible()
        weatherPage.checkNotVisible()

        errorPage.clickRetry()
        errorPage.checkNotVisible()

        weatherPage.checkCurrentWeather(
            city = "Moscow",
            temperature = "30°C"
        )
        weatherPage.checkHourWeather(
            position = 0,
            time = "00:00",
            temperature = "0°C"
        )
        weatherPage.checkHourWeather(
            position = 1,
            time = "01:00",
            temperature = "1°C",
        )
        weatherPage.checkHourWeather(
            position = 2,
            time = "02:00",
            temperature = "2°C",
        )
        weatherPage.checkDayWeather(
            position = 0,
            date = "1.12.2025",
            minTemperature = "-1°C",
            maxTemperature = "1°C"
        )
        weatherPage.checkDayWeather(
            position = 1,
            date = "2.12.2025",
            minTemperature = "-2°C",
            maxTemperature = "2°C"
        )
        weatherPage.checkDayWeather(
            position = 2,
            date = "3.12.2025",
            minTemperature = "-3°C",
            maxTemperature = "3°C"
        )
        progressPage.checkNotVisible()
        errorPage.checkNotVisible()

        activityScenarioRule.scenario.recreate()

        weatherPage.checkCurrentWeather(
            city = "Moscow",
            temperature = "30°C"
        )
        weatherPage.checkHourWeather(
            position = 0,
            time = "00:00",
            temperature = "0°C"
        )
        weatherPage.checkHourWeather(
            position = 1,
            time = "01:00",
            temperature = "1°C",
        )
        weatherPage.checkHourWeather(
            position = 2,
            time = "02:00",
            temperature = "2°C",
        )
        weatherPage.checkDayWeather(
            position = 0,
            date = "1.12.2025",
            minTemperature = "-1°C",
            maxTemperature = "1°C"
        )
        weatherPage.checkDayWeather(
            position = 1,
            date = "2.12.2025",
            minTemperature = "-2°C",
            maxTemperature = "2°C"
        )
        weatherPage.checkDayWeather(
            position = 2,
            date = "3.12.2025",
            minTemperature = "-3°C",
            maxTemperature = "3°C"
        )
        progressPage.checkNotVisible()
        errorPage.checkNotVisible()
    }
}