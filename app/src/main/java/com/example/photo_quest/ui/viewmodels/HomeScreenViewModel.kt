package com.example.photo_quest.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel: ViewModel() {

    private val _text = MutableStateFlow("This is home")
    val text = _text.asStateFlow()
}