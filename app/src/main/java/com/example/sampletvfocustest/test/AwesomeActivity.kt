package com.example.sampletvfocustest.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sampletvfocustest.R
import kotlinx.android.synthetic.main.activity_awesome.*
import java.util.*

class AwesomeActivity : Activity() {

    private var previouslyFocusedIndex: Int = -1
    private lateinit var collapsedToolbarScrollListener: RecyclerView.OnScrollListener

    fun performSmoothScrollToPosition(
        recyclerView: RecyclerView,
        scroller: SmoothScroller,
        focusedPosition: Int?
    ) {
        val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
        focusedPosition?.let { position ->
            // if (layoutManager.focusedChild !is ConstraintLayout && position >= 0) {
            scroller.startSmoothScroll(layoutManager, position)
            // }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awesome)
        checkFocus()
        initAdapter()
        initViews()
    }

    private fun initViews() {
        for (i in 1..6) {
            val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.menu_image_selector)
            imageView.isFocusable = true
            imageView.isFocusableInTouchMode = true
            val linLayPar = LinearLayout.LayoutParams(
                convertDpToPixels(50f, this).toInt(),
                convertDpToPixels(50f, this).toInt()
            )

            imageView.setOnFocusChangeListener { _, _ ->
                val find = llMenu.children.find { it.isFocused }
                if (find == null) {
                    Log.e("focus", "out focus $previouslyFocusedIndex")
                    // pageRecycler.scrollToPosition(previouslyFocusedIndex)
                    pageRecycler.scrollTo(0, previouslyFocusedIndex)
                } else {
                    Log.e("focus", "In focus $previouslyFocusedIndex")
                }
            }

            llMenu.addView(imageView, linLayPar)
        }
    }

    private fun initAdapter() {
        pageRecycler.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        pageRecycler.adapter = PageRecyclerAdapter()
        setOnScrollListener(true)
    }

    private fun setOnScrollListener(isAddListener: Boolean) {
        if (isAddListener) {
            collapsedToolbarScrollListener = object : RecyclerView.OnScrollListener() {
                val scroller = SmoothScroller(this@AwesomeActivity)
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val focusedPosition = recyclerView.focusedChild?.let { focusedView ->
                        recyclerView.getChildAdapterPosition(focusedView)
                    }
                    performSmoothScrollToPosition(recyclerView, scroller, focusedPosition)
                        focusedPosition?.let {
                            previouslyFocusedIndex =dy
                        }
                }
            }
            pageRecycler.addOnScrollListener(collapsedToolbarScrollListener)
        } else {
            pageRecycler.removeOnScrollListener(collapsedToolbarScrollListener)
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
}
