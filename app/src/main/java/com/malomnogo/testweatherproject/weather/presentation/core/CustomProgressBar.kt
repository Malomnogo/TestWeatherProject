package com.malomnogo.testweatherproject.weather.presentation.core

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.malomnogo.testweatherproject.R

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ProgressBar(context, attrs, android.R.attr.progressBarStyle), ChangeVisibility {

    init {
        id = R.id.progressBar
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

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