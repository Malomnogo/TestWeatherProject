package com.malomnogo.presentation.core.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.malomnogo.presentation.core.LoadPicEngine
import com.malomnogo.presentation.core.ProvideLoadPicEngine

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ShowPicture {

    private val picEngine: LoadPicEngine by lazy {
        (context.applicationContext as ProvideLoadPicEngine).picEngine()
    }

    private var url: String = ""

    override fun show(url: String) {
        picEngine.show(this, url)
        this.url = url
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = CustomImageViewSavedState(it)
            state.save(url)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as CustomImageViewSavedState?
        super.onRestoreInstanceState(restoredState?.superState)
        restoredState?.restore()?.let {
            show(it)
        }
    }

    private class CustomImageViewSavedState : View.BaseSavedState {

        private var imageUrl = ""

        constructor(superState: Parcelable) : super(superState)

        private constructor(parcelIn: Parcel) : super(parcelIn) {
            imageUrl = parcelIn.readString() ?: ""
        }

        fun restore() = imageUrl

        fun save(url: String) {
            imageUrl = url
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(imageUrl)
        }

        override fun describeContents() = 0

        companion object CREATOR : Parcelable.Creator<CustomImageViewSavedState> {
            override fun createFromParcel(parcel: Parcel): CustomImageViewSavedState =
                CustomImageViewSavedState(parcel)

            override fun newArray(size: Int): Array<CustomImageViewSavedState?> =
                arrayOfNulls(size)
        }
    }
}
