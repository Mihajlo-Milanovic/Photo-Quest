package com.example.photo_quest.ui.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo_quest.data.sources.RemoteImageDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewQuestScreenViewModel @Inject constructor(
    val remoteImageDataSource: RemoteImageDataSource
): ViewModel() {

    var imageUri by mutableStateOf<Uri>(Uri.EMPTY)

    fun getImageUri() = viewModelScope.launch {
        imageUri = remoteImageDataSource.getQuestImage("profile-pics/poz.jpg")
    }
}