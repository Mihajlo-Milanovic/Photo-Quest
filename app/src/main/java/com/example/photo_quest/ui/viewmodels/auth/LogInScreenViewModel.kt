package com.example.photo_quest.ui.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LogInScreenViewModel: ViewModel() {

    var emailOrPhone by mutableStateOf("")
    var password by mutableStateOf("")
    var showPassword by mutableStateOf(false)
}