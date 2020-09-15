package com.example.sampletvfocustest.providers

import android.util.Log
import com.example.sampletvfocustest.data.SportsItem
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseSportsDataFactory : SportsDataProvider {

    var firestoreDatabase = FirebaseFirestore.getInstance()

    override fun fetchSportsList(): List<SportsItem> {
        firestoreDatabase.collection(COLLECTION_NAME)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.forEach {
                        Log.e("tag", "data : ${it.data["id"]}")
                    }
                }
            }

        return emptyList()
    }

    companion object {
        private const val COLLECTION_NAME = "sports_list"
    }
}