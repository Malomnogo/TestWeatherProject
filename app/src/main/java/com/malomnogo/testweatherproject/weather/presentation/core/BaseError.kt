package com.malomnogo.testweatherproject.weather.presentation.core

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.TextViewCompat
import com.google.android.material.R as MaterialR
import com.malomnogo.testweatherproject.R

class BaseError @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), ShowError {

    private val errorTextView: ColorfulTextView
    val retryButton: RetryButton

    init {
        id = R.id.error
        orientation = VERTICAL
        gravity = Gravity.CENTER
        val padding = resources.getDimensionPixelSize(R.dimen.big_padding)
        setPadding(padding, padding, padding, padding)

        errorTextView = ColorfulTextView(context).apply {
            id = R.id.errorTextView
            TextViewCompat.setTextAppearance(this, MaterialR.style.TextAppearance_MaterialComponents_Body1)
            gravity = Gravity.CENTER
        }

        retryButton = RetryButton(context)

        addView(errorTextView)
        addView(retryButton)
    }

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val visibilityState = VisibilityState(it)
        visibilityState.save(this)
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as VisibilityState?
        super.onRestoreInstanceState(savedState?.superState)
        savedState?.restore(this)
    }

    override fun change(visible: Boolean) {
        this.visibility = if (visible) VISIBLE else GONE
    }

    fun showError(message: String) {
        errorTextView.show(message)
        errorTextView.showRed()
    }
}
