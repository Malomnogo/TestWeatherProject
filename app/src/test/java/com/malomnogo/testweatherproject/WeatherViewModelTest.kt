package com.malomnogo.testweatherproject

import com.malomnogo.testweatherproject.weather.domain.WeatherDomain
import com.malomnogo.testweatherproject.weather.domain.WeatherRepository
import com.malomnogo.testweatherproject.weather.presentation.FormatWeather
import com.malomnogo.testweatherproject.weather.presentation.UiObservable
import com.malomnogo.testweatherproject.weather.presentation.UpdateUi
import com.malomnogo.testweatherproject.weather.presentation.WeatherUiState
import com.malomnogo.testweatherproject.weather.presentation.WeatherViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var runAsync: FakeRunAsync
    private lateinit var uiObservable: FakeUiObservable
    private lateinit var repository: FakeRepository
    private lateinit var order: Order
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        order = Order()
        runAsync = FakeRunAsync(order)
        uiObservable = FakeUiObservable(order)
        repository = FakeRepository(order)
        val formatter = FormatWeather.Base()
        val mapper = object : WeatherDomain.Mapper<WeatherUiState> {
            override fun mapSuccess(
                city: String,
                temperature: Double
            ) = WeatherUiState.Success(
                city = city,
                temperature = formatter.formatWeather(temperature)
            )

            override fun mapError(message: String) = WeatherUiState.Error(
                message = message
            )
        }
        viewModel = WeatherViewModel(
            runAsync = runAsync,
            uiObservable = uiObservable,
            repository = repository,
            mapper = mapper
        )
    }

    @Test
    fun testFirstRun() {
        viewModel.init(isFirstOpen = true)
        assertEquals(listOf(WeatherUiState.Initial), uiObservable.states)
        assertEquals(Order(mutableListOf(OBSERVABLE_UPDATE)), order)

        val uiCallback = object : UpdateUi {
            override fun updateUi(uiState: WeatherUiState) = Unit
        }
        viewModel.startGettingUpdates(uiCallback)
        assertEquals(listOf(uiCallback), uiObservable.observers)
        assertEquals(
            Order(
                mutableListOf(OBSERVABLE_UPDATE, OBSERVABLE_UPDATE_OBSERVER, OBSERVABLE_UPDATE)
            ), order
        )

        viewModel.stopGettingUpdates()
        assertEquals(
            listOf(uiCallback, UpdateUi.Empty),
            uiObservable.observers
        )
        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, OBSERVABLE_UPDATE_OBSERVER, OBSERVABLE_UPDATE,
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
    fun testSuccess() {
        repository.result = WeatherDomain.Success(
            city = "Moscow",
            temperature = 30.0
        )
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
                WeatherUiState.Success(
                    city = "Moscow",
                    temperature = "30°C"
                )
            ), uiObservable.states
        )

        assertEquals(
            Order(
                mutableListOf(
                    OBSERVABLE_UPDATE, RUN_ASYNC_BACKGROUND, REPOSITORY_LOAD_DATA,
                    RUN_ASYNC_UI, OBSERVABLE_UPDATE
                ),
            ), order
        )
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

        repository.result = WeatherDomain.Success(
            city = "Moscow",
            temperature = 30.0
        )
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

        assertEquals(
            listOf(
                WeatherUiState.Progress,
                WeatherUiState.Error("No internet connection"),
                WeatherUiState.Progress,
                WeatherUiState.Success(
                    city = "Moscow",
                    temperature = "30°C"
                )
            ),
            uiObservable.states
        )

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

private class FakeUiObservable(private val order: Order) : UiObservable {

    val states = mutableListOf<WeatherUiState>()
    val observers = mutableListOf<UpdateUi>()
    override fun clear() = Unit

    override fun updateUi(uiState: WeatherUiState) {
        order.add(OBSERVABLE_UPDATE)
        states.add(uiState)
    }

    override fun updateObserver(observer: UpdateUi) {
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