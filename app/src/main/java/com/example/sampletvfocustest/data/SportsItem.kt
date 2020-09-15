package com.example.sampletvfocustest.data

data class SportsItem(
    val id: Int = -1,
    val tag: String = "",
    val title: String = "",
    val subTitle: String = "",
    val description: String = "",
    val duration: String = "",
    val imageUrl: String = "",
    val channelLogo: String = "",
    val isLive: Boolean = false
)
