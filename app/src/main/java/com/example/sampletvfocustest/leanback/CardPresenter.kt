package com.example.sampletvfocustest.leanback

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.sampletvfocustest.data.Movie
import com.example.sampletvfocustest.interfaces.OnItemFocused
import com.example.sampletvfocustest.R
import kotlin.properties.Delegates

/**
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an ImageCardView.
 */
class CardPresenter(private val onFocus: OnItemFocused? = null) : Presenter() {
    private lateinit var mContext: Context
    private var mDefaultCardImage: Drawable? = null
    private var sSelectedBackgroundColor: Int by Delegates.notNull()
    private var sDefaultBackgroundColor: Int by Delegates.notNull()

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        sDefaultBackgroundColor = ContextCompat.getColor(
            parent.context,
            R.color.default_background
        )
        sSelectedBackgroundColor =
            ContextCompat.getColor(
                parent.context,
                R.color.selected_background
            )
        mDefaultCardImage = ContextCompat.getDrawable(
            parent.context,
            R.drawable.movie
        )

        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }
        mContext = parent.context
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        updateCardBackgroundColor(cardView, false)
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val movie = item as Movie
        val cardView = viewHolder.view as ImageCardView

        if (movie.cardImageUrl != null) {
            cardView.titleText = movie.title
            cardView.contentText = movie.studio
            cardView.setMainImageDimensions(
                CARD_WIDTH,
                CARD_HEIGHT
            )
            loadImage(viewHolder.view as ImageCardView)
        }
        setCardFocusListener(cardView, movie)
    }

    private fun loadImage(view: ImageCardView) {
        Glide.with(view.context)
            .load("https://homepages.cae.wisc.edu/~ece533/images/girl.png")
            .centerCrop()
            .error(mDefaultCardImage)
            .into(view.mainImageView)
    }

    /**
     * Animate card view on focus change
     */
    private fun setCardFocusListener(
        cardView: ImageCardView,
        movie: Movie
    ) {
        cardView.setOnFocusChangeListener { _, hasFocus ->
            setCardBackground(cardView, hasFocus)
            onFocus?.onFocus(movie)
        }
    }

    private fun setCardBackground(
        cardView: ImageCardView,
        hasFocus: Boolean
    ) {
        cardView.background = if (hasFocus) {
            ContextCompat.getDrawable(
                mContext,
                R.drawable.euro_rail_selected
            )
        } else {
            ContextCompat.getDrawable(
                mContext,
                R.drawable.euro_rail_unselected
            )
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color)
        view.setInfoAreaBackgroundColor(color)
    }

    companion object {
        private val TAG = "CardPresenter"

        private val CARD_WIDTH = 480
        private val CARD_HEIGHT = 176
    }
}
