package com.example.sampletvfocustest.providers

import com.example.sampletvfocustest.data.MenuItem
import com.example.sampletvfocustest.data.SportsItem

interface SportsDataProvider {

    fun fetchSportsList(response: (List<SportsItem>) -> Unit)

    fun fetchMenus(response: (List<MenuItem>) -> Unit)
}
