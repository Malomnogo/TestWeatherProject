package com.malomnogo.testweatherproject.weather.presentation.core

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.malomnogo.testweatherproject.weather.presentation.UpdateUi
import com.malomnogo.testweatherproject.weather.presentation.WeatherUiState
import com.malomnogo.testweatherproject.weather.presentation.WeatherViewModel

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var uiCallBack: UpdateUi

    private lateinit var progressBar: CustomProgressBar
    private lateinit var errorView: BaseError
    private lateinit var weatherLayout: WeatherLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = viewModel(WeatherViewModel::class.java)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        progressBar = CustomProgressBar(this)
        weatherLayout = WeatherLayout(this)
        errorView = BaseError(this)
        errorView.setOnClickListener { viewModel.load() }

        rootLayout.addView(progressBar)
        rootLayout.addView(errorView)
        rootLayout.addView(weatherLayout)

        setContentView(rootLayout)

        uiCallBack = object : UpdateUi {
            override fun updateUi(uiState: WeatherUiState) {
                uiState.update(
                    progressBar = progressBar,
                    errorView = errorView,
                    weatherLayout = weatherLayout
                )
            }
        }

        viewModel.init(isFirstOpen = savedInstanceState == null)

    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(uiCallBack)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
        return (application as ProvideViewModel).viewModel(viewModelClass)
    }
}