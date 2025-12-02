package com.malomnogo.testweatherproject.weather.presentation.core

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.malomnogo.testweatherproject.R
import com.malomnogo.testweatherproject.weather.presentation.UpdateUi
import com.malomnogo.testweatherproject.weather.presentation.WeatherUiState
import com.malomnogo.testweatherproject.weather.presentation.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var uiCallBack: UpdateUi

    private lateinit var progressBar: CustomProgressBar
    private lateinit var errorView: BaseError
    private lateinit var weatherLayout: WeatherLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        progressBar = CustomProgressBar(this).apply {
            id = R.id.progressBar
        }

        errorView = BaseError(this)

        weatherLayout = WeatherLayout(this)

        rootLayout.addView(progressBar)
        rootLayout.addView(errorView)
        rootLayout.addView(weatherLayout)

        setContentView(rootLayout)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        uiCallBack = object : UpdateUi {
            override fun updateUi(uiState: WeatherUiState) {
                uiState.update(
                    progressBar,
                    errorView,
                    weatherLayout
                )
            }
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(uiCallBack)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}