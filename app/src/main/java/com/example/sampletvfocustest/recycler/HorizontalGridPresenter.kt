package com.example.sampletvfocustest.recycler

import android.util.Log
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.sampletvfocustest.R
import com.example.sampletvfocustest.utils.convertDpToPixels

class HorizontalGridPresenter : Presenter() {

    private val TAG = HorizontalGridPresenter::class.java.canonicalName
    private var cardWidth = 0
    private var cardHeight = 0

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val cardView = object : ImageCardView(parent?.context) {}

        cardWidth = convertDpToPixels(206f, parent!!.context).toInt()
        cardHeight = convertDpToPixels(125f, parent.context).toInt()

        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val cardView = viewHolder?.view as ImageCardView

        Log.d(TAG, "onBindViewHolder")

        cardView.titleText = "Awesome title"
        cardView.contentText = "Being Awesome"
        cardView.setMainImageDimensions(cardWidth, cardHeight)

        viewHolder.view?.context?.let { context ->
            Glide.with(context)
                .load("https://homepages.cae.wisc.edu/~ece533/images/girl.png")
                .centerCrop()
                .error(ContextCompat.getDrawable(cardView.context, R.mipmap.ic_launcher))
                .into(cardView.mainImageView)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        Log.d(TAG, "onUnbindViewHolder")
        val cardView = viewHolder?.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
        cardView.clearFocus()
        cardView.isFocusable = false
        cardView.isFocusableInTouchMode = false
        cardView.removeAllViews()
        cardView.clearDisappearingChildren()
    }
}
