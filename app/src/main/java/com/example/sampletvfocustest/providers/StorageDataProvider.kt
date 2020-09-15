package com.example.sampletvfocustest.providers

interface StorageDataProvider {

    fun fetchUrl(iconName: String, response: (url: String) -> Unit)
}
