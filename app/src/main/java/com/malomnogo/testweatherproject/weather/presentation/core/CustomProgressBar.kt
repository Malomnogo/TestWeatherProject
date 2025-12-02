package com.malomnogo.testweatherproject.weather.presentation.core

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ProgressBar

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ProgressBar(context, attrs, defStyleAttr), ChangeVisibility {

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val visibilityState = VisibilityState(it)
        visibilityState.save(this)
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val progressState = state as VisibilityState?
        super.onRestoreInstanceState(progressState?.superState)
        progressState?.restore(this)
    }

    override fun change(visible: Boolean) {
        this.visibility = if (visible) VISIBLE else GONE
    }
}