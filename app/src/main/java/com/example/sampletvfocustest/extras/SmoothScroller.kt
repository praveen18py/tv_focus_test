package com.example.sampletvfocustest.extras

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SmoothScroller(context: Context) {
    private val smoothScroller: RecyclerView.SmoothScroller =
        object : LinearSmoothScroller(context) {
            //This controls the direction in which smoothScroll looks for your view
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF {
                return PointF(
                    POSITION_X,
                    POSITION_Y
                )
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }

            override fun getVerticalSnapPreference(): Int = SNAP_TO_START

        }

    fun startSmoothScroll(layoutManager: StaggeredGridLayoutManager, position: Int) {
        smoothScroller.targetPosition = position
        layoutManager.startSmoothScroll(smoothScroller)
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 75f
        private const val POSITION_X = 0F
        private const val POSITION_Y = 1F
    }
}
