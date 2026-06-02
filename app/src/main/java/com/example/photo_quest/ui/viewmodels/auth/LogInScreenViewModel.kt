package com.example.photo_quest.ui.viewmodels.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LogInScreenViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var password2 by mutableStateOf("")
    var showPassword by mutableStateOf(false)
    var showSignUp by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var showResetPassword by mutableStateOf(true)
    var showResetPasswordDialog by mutableStateOf(false)

    fun authenticate(context: Context, goToHome: () -> Unit) {

        loading = true
        if (showSignUp) {
            signUp(context, goToHome)
        } else {
            logIn(context, goToHome)
        }
    }

    fun signUp(context: Context, goToHome: () -> Unit) {

        if (!checkPasswords(context)){
            loading = false
            return
        }
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loading = false
                if (task.isSuccessful) {
                    Log.d("AUTH::SIGN_UP", "createUserWithEmail:success")
                    goToHome()
                } else {
                    Log.w("AUTH::SIGN_UP", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun logIn(context: Context, goToHome: () -> Unit) {

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loading = false
                if (task.isSuccessful) {
                    Log.d("AUTH::LOG_IN", "signInWithEmail:success")
                    goToHome()
                } else {
                    Log.w("AUTH::LOG_IN", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    showResetPassword = true
                }
            }
    }

    fun checkPasswords(context: Context) : Boolean{

        if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$"))){
            Toast.makeText(context, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != password2) {
            Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun resetPasswordResetEmail(context: Context) {

        Firebase.auth.useAppLanguage()
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Email sent to $email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to send email. Check for typos", Toast.LENGTH_SHORT).show()
                }
            }
        showResetPasswordDialog = false
    }
}