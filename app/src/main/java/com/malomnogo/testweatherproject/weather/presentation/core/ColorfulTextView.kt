package com.malomnogo.testweatherproject.weather.presentation.core

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.malomnogo.testweatherproject.R

class ColorfulTextView : AppCompatTextView, ColorfulTextActions {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = TextViewColorSavedState(it)
            state.save(this)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as TextViewColorSavedState?
        super.onRestoreInstanceState(restoredState?.superState)
        restoredState?.restore(this)
    }

    override fun show(text: String) {
        setText(text)
    }

    override fun showBlack() {
        setTextColor(ContextCompat.getColor(context, R.color.black))
    }

    override fun showRed() {
        setTextColor(ContextCompat.getColor(context, R.color.red))
    }
}

private class TextViewColorSavedState : View.BaseSavedState {

    private var textColorRes = 0

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        textColorRes = parcelIn.readInt()
    }

    fun restore(textView: TextView) {
        textView.setTextColor(textColorRes)
    }

    fun save(textView: TextView) {
        textColorRes = textView.currentTextColor
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(textColorRes)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<TextViewColorSavedState> {
        override fun createFromParcel(parcel: Parcel): TextViewColorSavedState =
            TextViewColorSavedState(parcel)

        override fun newArray(size: Int): Array<TextViewColorSavedState?> =
            arrayOfNulls(size)
    }
}