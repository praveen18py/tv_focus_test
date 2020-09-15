package com.example.sampletvfocustest.providers

import com.example.sampletvfocustest.data.MenuItem
import com.example.sampletvfocustest.data.SportsItem
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseSportsDataFactory : SportsDataProvider {

    var firestoreDatabase = FirebaseFirestore.getInstance()

    override fun fetchSportsList(response: (List<SportsItem>) -> Unit) {
        fetchQuerySnapshot(COLLECTION_SPORTS_LIST, response)
    }


    override fun fetchMenus(response: (List<MenuItem>) -> Unit) {
        fetchQuerySnapshot(COLLECTION_MENUS, response)
    }

    private inline fun <reified T> fetchQuerySnapshot(
        collectionName: String,
        crossinline response: (list: List<T>) -> Unit
    ) {
        firestoreDatabase.collection(collectionName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<T>()
                    task.result?.let { snapshot ->
                        snapshot.forEach {
                            val item = it.toObject(T::class.java)
                            list.add(item)
                            response.invoke(list)
                        }
                    }
                }
            }
    }

    companion object {
        private const val COLLECTION_SPORTS_LIST = "sports_list"
        private const val COLLECTION_MENUS = "menus"
    }
}
