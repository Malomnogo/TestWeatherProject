package com.malomnogo.testweatherproject.weather

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.malomnogo.presentation.core.LoadPicEngine
import com.malomnogo.testweatherproject.R

class BaseLoadPicEngine : LoadPicEngine {

    override fun show(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context).load(imageUrl).into(imageView)
    }
}

class MockLoadPicEngine : LoadPicEngine {

    override fun show(imageView: ImageView, imageUrl: String) {
        imageView.setImageResource(R.drawable.ic_launcher_foreground)
    }
}