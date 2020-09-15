package com.example.sampletvfocustest.providers

import com.example.sampletvfocustest.data.SportsItem

interface SportsDataProvider {

    fun fetchSportsList() : List<SportsItem>
}