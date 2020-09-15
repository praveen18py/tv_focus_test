package com.example.sampletvfocustest.recycler

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sampletvfocustest.R
import com.example.sampletvfocustest.data.MenuItem
import com.example.sampletvfocustest.data.SportsItem
import com.example.sampletvfocustest.extras.SmoothScroller
import com.example.sampletvfocustest.providers.ImageProvider
import com.example.sampletvfocustest.providers.SportsDataProvider
import com.example.sampletvfocustest.utils.animateOnFocus
import com.example.sampletvfocustest.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_recycler_approach.*
import org.koin.android.ext.android.inject
import java.util.*

class RecyclerApproachActivity : Activity() {

    private var previouslyFocusedIndex: Int = -1
    private lateinit var collapsedToolbarScrollListener: RecyclerView.OnScrollListener
    private var currentFocusedView: View? = null

    private val sportsDataProvider by inject<SportsDataProvider>()
    private val imageProvider by inject<ImageProvider>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_approach)

        sportsDataProvider.fetchSportsList(::handleSportsListResponse)
        sportsDataProvider.fetchMenus(::handleMenuData)
        initViews()
    }

    private fun handleMenuData(list: List<MenuItem>) {
        llMenu.removeAllViews()
        for (item in list) {
            val imageView = ImageView(this)
            imageProvider.setImageFromFirebase(imageView, item.icon)
            imageView.isFocusable = true
            imageView.isFocusableInTouchMode = true
            val linLayPar = LinearLayout.LayoutParams(
                convertDpToPixels(40f, this).toInt(),
                convertDpToPixels(40f, this).toInt()
            )
            linLayPar.bottomMargin = convertDpToPixels(10f, this).toInt()

            imageView.setOnFocusChangeListener { view, b ->
                view.animateOnFocus(b, 1.5f, 1.5f)
            }
            llMenu.addView(imageView, linLayPar)
        }
    }

    private fun handleSportsListResponse(sportsList: List<SportsItem>) {
        Log.e("tag", sportsList.toString())
    }

    private fun initViews() {
        initAdapter()
        checkFocus()
    }

    private fun initAdapter() {
        pageRecycler.layoutManager =
            StaggeredGridLayoutManager(
                1, StaggeredGridLayoutManager.VERTICAL
            )
        pageRecycler.adapter = PageRecyclerAdapter()
        setOnScrollListener(true)
    }

    private fun setOnScrollListener(isAddListener: Boolean) {
        if (isAddListener) {
            collapsedToolbarScrollListener = object : RecyclerView.OnScrollListener() {
                val scroller = SmoothScroller(this@RecyclerApproachActivity)
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    scrollToPosition(recyclerView, scroller)
                    Log.e("previouslyFocusedIndex", "setOnScrollListener ")
                }
            }
            pageRecycler.addOnScrollListener(collapsedToolbarScrollListener)
        } else {
            pageRecycler.removeOnScrollListener(collapsedToolbarScrollListener)
        }
    }

    private fun scrollToPosition(
        recyclerView: RecyclerView,
        scroller: SmoothScroller
    ) {
        val focusedPosition = recyclerView.focusedChild?.let { focusedView ->
            currentFocusedView = focusedView
            recyclerView.getChildAdapterPosition(focusedView)
        }
        performSmoothScrollToPosition(recyclerView, scroller, focusedPosition)
        focusedPosition?.let {
            previouslyFocusedIndex = focusedPosition
        }
    }

    private fun performSmoothScrollToPosition(
        recyclerView: RecyclerView,
        scroller: SmoothScroller,
        focusedPosition: Int?
    ) {
        val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
        focusedPosition?.let { position ->
            scroller.startSmoothScroll(layoutManager, position)
        }
    }

    private fun checkFocus() {
        val timerObj = Timer()
        val timerTaskObj = object : TimerTask() {
            override fun run() {
                Log.i("SearchFocus", "Current focused view : $currentFocus")
            }
        }
        timerObj.schedule(timerTaskObj, 0, 2000)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, RecyclerApproachActivity::class.java)
    }
}
