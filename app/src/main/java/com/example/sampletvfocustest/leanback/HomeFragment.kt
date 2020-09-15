package com.example.sampletvfocustest.leanback

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.example.sampletvfocustest.*
import com.example.sampletvfocustest.data.Movie
import com.example.sampletvfocustest.data.MovieList
import com.example.sampletvfocustest.interfaces.OnItemFocused
import java.util.*


class HomeFragment : BrowseFragment() {
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)

        prepareBackgroundManager()

        setupUIElements()

        loadRows()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun prepareBackgroundManager() {
        activity?.let { it ->
            mBackgroundManager = BackgroundManager.getInstance(activity)
            mBackgroundManager.attach(it.window)
            mDefaultBackground = ContextCompat.getDrawable(
                it,
                R.drawable.default_background
            )
            mMetrics = DisplayMetrics()
            it.windowManager.defaultDisplay.getMetrics(mMetrics)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        headersState = HEADERS_DISABLED
    }

    private fun setupUIElements() {
//        title = getString(R.string.browse_title)
        // over title
        headersState = HEADERS_DISABLED
        isHeadersTransitionOnBackEnabled = true

        activity?.let { it ->
            // set fastLane (or headers) background color
            brandColor = ContextCompat.getColor(
                it,
                R.color.fastlane_background
            )
            // set search icon color
            searchAffordanceColor = ContextCompat.getColor(
                it,
                R.color.search_opaque
            )
        }

    }

    companion object {
        private val TAG = "MainFragment"
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }

    private fun loadRows() {
        val list = MovieList.list

        val rowsAdapter = ArrayObjectAdapter(
            CustomListRowPresenter(
                FocusHighlight.ZOOM_FACTOR_NONE,
                false
            ).apply {
                rowHeight = ViewGroup.LayoutParams.WRAP_CONTENT
                setNumRows(1)
                shadowEnabled = false
                enableChildRoundedCorners(false)
            }
        )

        for (i in 0 until NUM_ROWS) {
            if (i != 0) {
                Collections.shuffle(list)
            }
            val listRowAdapter = ArrayObjectAdapter(CardPresenter(handleCardFocusListener()))
            for (j in 0 until NUM_COLS) {
                listRowAdapter.add(list[j % 5])
            }
            val header = HeaderItem(i.toLong(), MovieList.MOVIE_CATEGORY[i])
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }

        adapter = rowsAdapter
    }

    private fun handleCardFocusListener() = object :
        OnItemFocused {
        override fun onFocus(data: Movie) {
            Toast.makeText(activity.baseContext, data.title, Toast.LENGTH_LONG).show()
            val imageHero = view?.findViewById<ImageView>(R.id.imageHero)
            val tvHeroTitle = view?.findViewById<TextView>(R.id.tvHeroTitle)
            val tvHeroDescription = view?.findViewById<TextView>(R.id.tvHeroDescription)
            tvHeroTitle?.text = data.title
            tvHeroDescription?.text = data.description
            imageHero?.let { view ->
                Glide.with(activity.baseContext)
                    .load("https://homepages.cae.wisc.edu/~ece533/images/girl.png")
                    .centerCrop()
                    .error(
                        ContextCompat.getDrawable(
                            activity.baseContext,
                            R.drawable.movie
                        )
                    )
                    .into(view)
            }
        }

    }
}