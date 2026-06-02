package com.example.photo_quest.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Home : Route, NavKey

    @Serializable
    data object LogIn : Route, NavKey

//    @Serializable
//    data object SignUp : Route, NavKey


    // data object for routes that take no parameter
    // data class for routes that take some parameters
}