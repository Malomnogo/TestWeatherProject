package com.malomnogo.testweatherproject

import com.malomnogo.domain.ConditionDomain
import com.malomnogo.domain.DayDomain
import com.malomnogo.domain.ForecastDayDomain
import com.malomnogo.domain.ForecastDomain
import com.malomnogo.domain.HourDomain
import com.malomnogo.domain.TemperatureDomain
import com.malomnogo.domain.WeatherDomain
import com.malomnogo.domain.WeatherRepository
import com.malomnogo.presentation.core.FormatWeather
import com.malomnogo.presentation.weather.mapper.BaseTemperatureDomainMapper
import com.malomnogo.presentation.weather.mapper.BaseWeatherDomainToUiMapper
import com.malomnogo.presentation.core.mapper.BaseConditionDomainMapper
import com.malomnogo.presentation.weather.mapper.BaseForecastDomainToUiMapper
import com.malomnogo.presentation.day.mapper.BaseForecastDayDomainMapper
import com.malomnogo.presentation.day.mapper.DayDomainToUiMapper
import com.malomnogo.presentation.hour.mapper.HourDomainToUiMapper
import com.malomnogo.presentation.core.FormatDate
import com.malomnogo.presentation.core.FormatTime
import com.malomnogo.presentation.weather.WeatherUiObservable
import com.malomnogo.presentation.weather.UpdateWeatherUi
import com.malomnogo.presentation.weather.WeatherUiState
import com.malomnogo.presentation.weather.WeatherViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiObservable: FakeWeatherUiObservable
    private lateinit var repository: FakeRepository
    private lateinit var order: Order
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        order = Order()
        runAsync = FakeRunAsync(order)
        uiObservable = FakeWeatherUiObservable(order)
        repository = FakeRepository(order)
          val formatter = FormatWeather.Base()
          val formatDate = FormatDate.Base()
          val mapper = BaseWeatherDomainToUiMapper(
              temperatureMapper = BaseTemperatureDomainMapper(
                  formatWeather = formatter,
                  conditionMapper = BaseConditionDomainMapper()
              ),
        forecastMapper = BaseForecastDomainToUiMapper(
            forecastDayMapper = BaseForecastDayDomainMapper(
                formatDate = formatDate,
                dayMapper = DayDomainToUiMapper(
                    formatWeather = formatter,
                    conditionMapper = BaseConditionDomainMapper()
                ),
                hourMapper = HourDomainToUiMapper(
                    formatTime = FormatTime.Base(),
                    formatWeather = formatter
                )
            )
        )
          )
        viewModel = WeatherViewModel(
            runAsync = runAsync,
            uiObservable = uiObservable,
            repository = repository,
            mapper = mapper
        )
    }

    @Test
    fun testFirstRun() {
        repository.result = createSuccessWeatherDomain()
        viewModel.init(isFirstOpen = true)
        assertEquals(listOf(WeatherUiState.Progress), uiObservable.states)
        assertEquals(
            Order(
                mutableListOf(OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA)
            ), order
        )

        runAsync.returnResult()
        assertEquals(WeatherUiState.Progress, uiObservable.states[0])
        val expected = createExpectedSuccessState()
        assertEquals(expected, uiObservable.states[1])

        val uiCallback = object : UpdateWeatherUi {
            override fun updateUi(uiState: WeatherUiState) = Unit
        }
        viewModel.startGettingUpdates(uiCallback)
        assertEquals(listOf(uiCallback), uiObservable.observers)
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE,
                    OBSERVABLE_UPDATE_OBSERVER, OBSERVABLE_UPDATE
                )
            ), order
        )

        viewModel.stopGettingUpdates()
        assertEquals(
            listOf(uiCallback, UpdateWeatherUi.Empty),
            uiObservable.observers
        )
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE,
                    OBSERVABLE_UPDATE_OBSERVER, OBSERVABLE_UPDATE,
                    OBSERVABLE_UPDATE_OBSERVER, OBSERVABLE_UPDATE
                )
            ), order
        )
    }

    @Test
    fun testNotFirstRun() {
        viewModel.init(isFirstOpen = false)
        assertEquals(emptyList<WeatherUiState>(), uiObservable.states)
        assertEquals(Order(), order)
    }

    @Test
    fun testError() {
        repository.result = WeatherDomain.Error(message = "No internet connection")
        viewModel.load()
        assertEquals(listOf(WeatherUiState.Progress), uiObservable.states)
        assertEquals(
            Order(
                mutableListOf(OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA)
            ), order
        )

        runAsync.returnResult()
        assertEquals(
            listOf(
                WeatherUiState.Progress,
                WeatherUiState.Error(message = "No internet connection")
            ), uiObservable.states
        )
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE
                )
            ), order
        )
    }

    @Test
    fun testSuccessAfterError() {
        repository.result = WeatherDomain.Error("No internet connection")
        viewModel.load()

        assertEquals(listOf(WeatherUiState.Progress), uiObservable.states)
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA
                )
            ),
            order
        )

        runAsync.returnResult()

        assertEquals(
            listOf(
                WeatherUiState.Progress,
                WeatherUiState.Error("No internet connection")
            ),
            uiObservable.states
        )
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE
                )
            ),
            order
        )

        repository.result = createSuccessWeatherDomain()
        viewModel.load()

        assertEquals(
            listOf(
                WeatherUiState.Progress,
                WeatherUiState.Error("No internet connection"),
                WeatherUiState.Progress
            ),
            uiObservable.states
        )
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE,
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA
                )
            ),
            order
        )

        runAsync.returnResult()

        assertEquals(WeatherUiState.Progress, uiObservable.states[0])
        assertEquals(WeatherUiState.Error("No internet connection"), uiObservable.states[1])
        assertEquals(WeatherUiState.Progress, uiObservable.states[2])
        val expected = createExpectedSuccessState()
        assertEquals(expected, uiObservable.states[3])

        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE,
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE
                )
            ),
            order
        )
    }
}

internal const val RUN_ASYNC_BACKGROUND = "RunAsync#runAsync{backgroundBlock.invoke()}"
internal const val RUN_ASYNC_UI = "RunAsync#runAsync{uiBlock.invoke(result)}"
private const val OBSERVABLE_UPDATE = "UiObservable#updateUi"
private const val OBSERVABLE_UPDATE_OBSERVER = "UiObservable#updateObserver"
private const val REPOSITORY_LOAD_DATA = "Repository#loadData"

private fun createExpectedSuccessState(): WeatherUiState {
    val formatter = FormatWeather.Base()
    val formatDate = FormatDate.Base()
    val formatTime = FormatTime.Base()
    
    val weatherDomain = createSuccessWeatherDomain()
    val mapper = BaseWeatherDomainToUiMapper(
        temperatureMapper = BaseTemperatureDomainMapper(
            formatWeather = formatter,
            conditionMapper = BaseConditionDomainMapper()
        ),
        forecastMapper = BaseForecastDomainToUiMapper(
            forecastDayMapper = BaseForecastDayDomainMapper(
                formatDate = formatDate,
                dayMapper = DayDomainToUiMapper(
                    formatWeather = formatter,
                    conditionMapper = BaseConditionDomainMapper()
                ),
                hourMapper = HourDomainToUiMapper(
                    formatTime = formatTime,
                    formatWeather = formatter
                )
            )
        )
    )
    
    return weatherDomain.map(mapper)
}

private fun createSuccessWeatherDomain(): WeatherDomain.Success {
    val baseTimeEpoch = 1764536400L // 1.12.2025 00:00
    val hourlyForecast = (0..2).map { index ->
        HourDomain.Success(
            timeEpoch = baseTimeEpoch + index * 3600L,
            tempC = index.toDouble()
        )
    }
    
    val dailyForecast = (0..2).map { index ->
        ForecastDayDomain.Success(
            dateEpoch = baseTimeEpoch + index * 86400L,
            day = DayDomain.Success(
                maxTempC = (index + 1).toDouble(),
                minTempC = -(index + 1).toDouble(),
                condition = ConditionDomain.Success(
                    text = "Sunny",
                    iconUrl = "http://icon$index.png"
                )
            ),
            hour = if (index == 0) hourlyForecast else emptyList()
        )
    }
    
    return WeatherDomain.Success(
        city = "Moscow",
        temperature = TemperatureDomain.Success(
            temperature = 30.0,
            condition = ConditionDomain.Success(text = "Sunny", iconUrl = "http://icon.png")
        ),
        forecast = ForecastDomain.Success(forecastDay = dailyForecast)
    )
}

private class FakeWeatherUiObservable(private val order: Order) : WeatherUiObservable {

    val states = mutableListOf<WeatherUiState>()
    val observers = mutableListOf<UpdateWeatherUi>()
    override fun clear() = Unit

    override fun updateUi(uiState: WeatherUiState) {
        order.add(OBSERVABLE_UPDATE)
        states.add(uiState)
    }

    override fun updateObserver(observer: UpdateWeatherUi) {
        order.add(OBSERVABLE_UPDATE_OBSERVER)
        observers.add(observer)
        updateUi(states.last())
    }
}

private class FakeRepository(private val order: Order) : WeatherRepository {

    lateinit var result: WeatherDomain

    override suspend fun loadData(): WeatherDomain {
        order.add(REPOSITORY_LOAD_DATA)
        return result
    }
}