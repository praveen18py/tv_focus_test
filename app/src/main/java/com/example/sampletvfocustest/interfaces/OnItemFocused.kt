package com.example.sampletvfocustest.interfaces

import com.example.sampletvfocustest.data.Movie

interface OnItemFocused {
    fun onFocus(data: Movie)
}