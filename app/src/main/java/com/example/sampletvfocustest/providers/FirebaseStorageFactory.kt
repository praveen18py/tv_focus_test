package com.example.sampletvfocustest.providers

import com.google.firebase.storage.FirebaseStorage

class FirebaseStorageFactory : StorageDataProvider {

    private val storage = FirebaseStorage.getInstance().reference

    override fun fetchUrl(iconName: String, response: (url: String) -> Unit) {
        storage.child(iconName).downloadUrl.addOnSuccessListener {
            response(it.toString())
        }
    }
}
