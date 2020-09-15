package com.example.sampletvfocustest.providers

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.sampletvfocustest.R
import org.koin.core.KoinComponent
import org.koin.core.inject

class GlideImageFactory : ImageProvider, KoinComponent {

    private val storageDataProvider by inject<StorageDataProvider>()

    override fun setImage(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .placeholder(ContextCompat.getDrawable(imageView.context, R.mipmap.ic_launcher))
            .error(ContextCompat.getDrawable(imageView.context, R.mipmap.ic_launcher))
            .into(imageView)
    }

    override fun setImageFromFirebase(imageView: ImageView, url: String) {
        storageDataProvider.fetchUrl(url, response = {
            setImage(imageView, it)
        })
    }
}