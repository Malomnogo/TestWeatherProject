package com.malomnogo.presentation.weather.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.malomnogo.presentation.R
import com.malomnogo.presentation.core.views.VisibilityState

class RetryButton : MaterialButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        id = R.id.retryButton
        text = context.getString(R.string.retry)
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState().let {
            val visibilityState = VisibilityState(it)
            visibilityState.save(this)
            return visibilityState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as VisibilityState?
        super.onRestoreInstanceState(savedState?.superState)
        savedState?.restore(this)
    }
}