package com.example.photo_quest.ui.viewmodels.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photo_quest.data.models.LogInState
import com.example.photo_quest.data.models.User
import com.example.photo_quest.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var password2 by mutableStateOf("")

    var uiState by mutableStateOf(LogInState())

    fun authenticate(context: Context, goToHome: () -> Unit) {

        uiState = uiState.copy(
            loading = true
        )

        if (user.value != null) {
            goToHome()
            uiState = uiState.copy(
                loading = false
            )
        }

        if (email.isEmpty()) {
            Toast.makeText(context, "Invalid e-mail address", Toast.LENGTH_SHORT).show()
            uiState = uiState.copy(
                loading = false
            )
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
            uiState = uiState.copy(
                loading = false
            )
            return
        }

        if (uiState.showSignUp) {
            signUp(context)
        } else {
            logIn(context)
        }
    }

    fun signUp(context: Context) {

        if (!checkPasswords(context)) {
            uiState = uiState.copy(
                loading = false
            )
            return
        }
        viewModelScope.launch {

            if (userRepository.signUp(email, password) != null) {
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                userRepository.updateUser(
                    user.value!!.copy(
                        name = username
                    )
                )
                sendVerificationEmail(context = context)

            } else
                Toast.makeText(context, "Sign up unsuccessful", Toast.LENGTH_SHORT).show()

            uiState = uiState.copy(
                loading = false
            )
        }
    }

    fun logIn(context: Context) = viewModelScope.launch {

        userRepository.reloadUser()
        val result = userRepository.logIn(email, password)
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()

        uiState = uiState.copy(
            loading = false
        )
    }


    fun checkPasswords(context: Context): Boolean {
        if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$"))) {
            uiState = uiState.copy(
                showPasswordRequirements = true
            )
            //Toast.makeText(context, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != password2) {
            Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun sendPasswordResetEmail(context: Context, email: String) = viewModelScope.launch {
        if (userRepository.sendPasswordResetEmail(email))
            Toast.makeText(context, "E-mail sent to $email", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Failed to send email. Check for typos.", Toast.LENGTH_SHORT)
                .show()
        uiState = uiState.copy(
            showResetPasswordDialog = false
        )
    }

    fun sendVerificationEmail(context: Context) = viewModelScope.launch {
        if (userRepository.sendVerificationEmail())
            user.value?.email?.let {
                Toast.makeText(
                    context,
                    "Verification e-mail sent to $it",
                    Toast.LENGTH_SHORT
                ).show()
            }
        else
            Toast.makeText(context, "Failed to send verification email.", Toast.LENGTH_SHORT).show()

    }

    fun reloadUser() = viewModelScope.launch {
        uiState = uiState.copy(
            loading = true
        )
        userRepository.reloadUser()
        uiState = uiState.copy(
            loading = false
        )
    }

    fun logOut() {
        userRepository.logOut()
        uiState = LogInState(
            showSignUp = true
        )
    }
}