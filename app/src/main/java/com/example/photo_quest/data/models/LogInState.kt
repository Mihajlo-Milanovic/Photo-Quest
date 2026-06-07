package com.example.photo_quest.data.models

data class LogInState(
    val showPassword: Boolean = false,
    val showSignUp: Boolean = false,
    val loading: Boolean = false,
    val showResetPassword: Boolean = false,
    val showResetPasswordDialog: Boolean = false,
    val showPasswordRequirements: Boolean = false,
    val userEmailUnverified: Boolean = false
)