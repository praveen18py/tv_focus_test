package com.example.sampletvfocustest.recycler

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sampletvfocustest.R
import com.example.sampletvfocustest.extras.SmoothScroller
import com.example.sampletvfocustest.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_awesome.*
import java.util.*

class RecyclerApproachActivity : Activity() {

    private var previouslyFocusedIndex: Int = -1
    private lateinit var collapsedToolbarScrollListener: RecyclerView.OnScrollListener

    private var currentFocusedView: View? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_awesome)
        checkFocus()
        initAdapter()
        initViews()
    }

    private fun initViews() {
        for (i in 1..5) {
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
                    Log.e("previouslyFocusedIndex", "out focus $previouslyFocusedIndex")
//                    pageRecycler.scrollToPosition(previouslyFocusedIndex)
//                    pageRecycler.scrollTo(0, previouslyFocusedIndex)
//                     pageRecycler.scrollToPosition(previouslyFocusedIndex)

//                    performSmoothScrollToPosition(
//                        pageRecycler,
//                        SmoothScroller(this@AwesomeActivity),
//                        previouslyFocusedIndex
//                    )

                    // pageRecycler.focusableViewAvailable(currentFocusedView)
                } else {
                    Log.e("previouslyFocusedIndex", "In focus $previouslyFocusedIndex")

                }
            }

            llMenu.addView(imageView, linLayPar)
        }
    }

    private fun initAdapter() {
        pageRecycler.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        pageRecycler.adapter =
            PageRecyclerAdapter()
        setOnScrollListener(true)

        // pageRecycler.preserveFocusAfterLayout = true

        pageRecycler.setOnFocusChangeListener { view, b ->
            println("view is $view, and it is $b")
//            currentFocusedView = view
        }
        pageRecycler.preserveFocusAfterLayout = true
    }

    private fun setOnScrollListener(isAddListener: Boolean) {
        if (isAddListener) {
            collapsedToolbarScrollListener = object : RecyclerView.OnScrollListener() {
                val scroller =
                    SmoothScroller(this@RecyclerApproachActivity)

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
