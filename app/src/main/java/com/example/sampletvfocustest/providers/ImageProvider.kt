package com.example.sampletvfocustest.providers

import android.widget.ImageView

interface ImageProvider {

    fun setImage(imageView: ImageView, url: String)

    fun setImageFromFirebase(imageView: ImageView, url: String)
}
