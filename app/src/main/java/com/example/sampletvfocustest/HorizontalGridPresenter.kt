package com.example.sampletvfocustest

import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide

class HorizontalGridPresenter : Presenter() {

    private val TAG = HorizontalGridPresenter::class.java.canonicalName

    private var CARD_WIDTH = 0
    private var CARD_HEIGHT = 0

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val cardView = object : ImageCardView(parent?.context) {
            override fun setSelected(selected: Boolean) {
                super.setSelected(selected)
                // Log.e(TAG, "Selected")
            }
        }

        CARD_WIDTH = convertDpToPixels(
            206f,
            parent!!.context
        ).toInt()
        CARD_HEIGHT = convertDpToPixels(
            125f,
            parent.context
        ).toInt()

        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val cardView = viewHolder?.view as ImageCardView

        Log.d(TAG, "onBindViewHolder")

        cardView.titleText = "Awesome title"
        cardView.contentText = "Being Awesome"
        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)
        Glide.with(viewHolder.view?.context)
            .load("https://homepages.cae.wisc.edu/~ece533/images/girl.png")
            .placeholder(ContextCompat.getDrawable(cardView.context, R.mipmap.ic_launcher))
            .centerCrop()
            .error(ContextCompat.getDrawable(cardView.context, R.mipmap.ic_launcher))
            .into(cardView.mainImageView)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        Log.d(TAG, "onUnbindViewHolder")
        val cardView = viewHolder?.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}
