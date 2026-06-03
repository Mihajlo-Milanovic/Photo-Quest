package com.example.photo_quest.ui.viewmodels.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo_quest.data.models.User
import com.example.photo_quest.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    val user: StateFlow<User?> = userRepository.user.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    var email by mutableStateOf(user.value?.email ?: "")
    var password by mutableStateOf("")
    var password2 by mutableStateOf("")
    var showPassword by mutableStateOf(false)
    var showSignUp by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var showResetPassword by mutableStateOf(false)
    var showResetPasswordDialog by mutableStateOf(false)
    var showResendVerificationEmail by mutableStateOf(false)
    var showVerifyEmailMessage by mutableStateOf(false)
    var showPasswordRequirements by mutableStateOf(false)

    fun authenticate(context: Context, goToHome: () -> Unit) {

        loading = true

        if(user.value != null) {
            goToHome()
            loading = false
        }

        if (email.isEmpty()){
            Toast.makeText(context, "Invalid e-mail address", Toast.LENGTH_SHORT).show()
            loading = false
            return
        }

        if (password.isEmpty()){
            Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
            loading = false
            return
        }

        if (showSignUp) {
            signUp(context)
        } else {
            logIn(context)
        }
    }

    fun signUp(context: Context) {

        if (!checkPasswords(context)){
            loading = false
            return
        }
        userRepository.signUp(context, email, password) { sendVerificationEmail(context = context) }
    }

    fun logIn(context: Context) = userRepository.logIn(context, email, password) {
        loading = false
    }


    fun checkPasswords(context: Context) : Boolean{
        if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$"))){
            showPasswordRequirements = true
            //Toast.makeText(context, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != password2) {
            Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun resetPasswordResetEmail(context: Context) {
        userRepository.sendPasswordResetEmail(email, context)
        showResetPasswordDialog = false
    }

    fun sendVerificationEmail(context: Context) = userRepository.sendVerificationEmail(context, viewModelScope)

}