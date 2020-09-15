package com.example.sampletvfocustest.leanback

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.leanback.widget.*
import androidx.leanback.widget.BaseGridView.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampletvfocustest.recycler.PageRecyclerAdapter
import com.example.sampletvfocustest.R
import com.example.sampletvfocustest.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_base_page.*

class BasePageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_page)

        initUI()
        initViews()
    }

    private fun initUI() {

        hgv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        hgv.adapter =
            PageRecyclerAdapter()
        hgv.apply {
            windowAlignment = WINDOW_ALIGN_LOW_EDGE
            windowAlignmentOffsetPercent = 0f
            windowAlignmentOffset = 0
            itemAlignmentOffsetPercent = 0f
        }
    }

    private fun getHeader(): List<HeaderItem> =
        listOf(
            HeaderItem("Rail Title 1"),
            HeaderItem("Rail Title 2"),
            HeaderItem("Rail Title 3"),
            HeaderItem("Rail Title 4"),
            HeaderItem("Rail Title 5"),
            HeaderItem("Rail Title 6"),
            HeaderItem("Rail Title 7"),
            HeaderItem("Rail Title 8"),
            HeaderItem("Rail Title 9"),
            HeaderItem("Rail Title 10")
        )

    private fun initViews() {
        for (i in 1..8) {
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
            }

            llMenu.addView(imageView, linLayPar)
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, BasePageActivity::class.java)
    }
}