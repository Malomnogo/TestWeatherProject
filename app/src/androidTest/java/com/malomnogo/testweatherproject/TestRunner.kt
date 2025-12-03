package com.malomnogo.testweatherproject

import android.app.Application
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.malomnogo.testweatherproject.weather.Mock

class TestRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle) {
        arguments.putString(
            "android.app",
            Mock::class.java.name
        )
        super.onCreate(arguments)
    }

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: android.content.Context?
    ): Application {
        return super.newApplication(cl, Mock::class.java.name, context)
    }
}

