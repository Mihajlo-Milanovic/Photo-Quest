package com.example.photo_quest.data.sources

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteImageDataSource @Inject constructor() {

    private val storage = Firebase.storage("gs://photo-quest-61d0f.firebasestorage.app")

    init {
        storage.useEmulator("localhost", 9199)
    }

    suspend fun getQuestImage(id: String): Uri {
        try {
            return storage.reference.child(id).downloadUrl.await()
        } catch (ex: Exception) {
            Log.e("IMG::DOWNLOAD", "Image download error", ex)
        }
        return Uri.EMPTY
    }
}