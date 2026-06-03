package com.example.photo_quest.data.sources

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.photo_quest.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteAuthDataSource @Inject constructor() {

    val user = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener { auth ->

            Log.d(
                "AUTH::STATE", "onAuthStateChanged: ${
                    auth.currentUser?.let { it ->
                        User(
                            name = it.displayName ?: "",
                            email = it.email ?: "",
                            photoUrl = it.photoUrl ?: Uri.EMPTY,
                            emailVerified = it.isEmailVerified,
                            uid = it.uid
                        )
                    } ?: "NULL"
                }")

            trySend(auth.currentUser)
        }

        Firebase.auth.addAuthStateListener(listener)

        awaitClose {
            Firebase.auth.removeAuthStateListener(listener)
        }
    }

    fun logIn(email: String, password: String) = Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                Log.d("AUTH::LOG_IN", "signInWithEmail:success")
            else
                Log.w("AUTH::LOG_IN", "signInWithEmail:failure", task.exception)
        }

    fun signUp(email: String, password: String) = Firebase.auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                Log.d("AUTH::SIGN_UP", "createUserWithEmail:success")
            else
                Log.w("AUTH::SIGN_UP", "createUserWithEmail:failure", task.exception)
        }

    fun logOut() = Firebase.auth.signOut()

    suspend fun updateUser(user: User) {

        val profileUpdates = userProfileChangeRequest {
            displayName = user.name
            photoUri = user.photoUrl
        }

        this.user.last()?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AUTH::USER_UPDATE", "User profile updated.")
                }
            }
    }

    suspend fun changeEmail(email: String) {
        user.last()?.verifyBeforeUpdateEmail(email)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AUTH::E-MAIL_CHANGE", "User email address updated.")
                }
            }
    }

    fun sendPasswordResetEmail(email: String, context: Context) {
        Firebase.auth.useAppLanguage()
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Email sent to $email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to send email. Check for typos", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun sendVerificationEmail(context: Context, coroutineScope: CoroutineScope) {
        Firebase.auth.useAppLanguage()

        coroutineScope.launch {
            user.collect { authUser ->
                authUser?.sendEmailVerification()?.addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        Toast.makeText(
                            context, "Verification e-mail sent to ${
                                authUser.email?.let {
                                    it.slice(IntRange(0, 2)) + "*****" +
                                            it.slice(IntRange(it.length - 10, it.length - 1))
                                }
                            }", Toast.LENGTH_SHORT
                        ).show()
                    else {
                        Log.e("AUTH::VERIFICATION_EMAIL", "Failed to send verification email: ${task.exception?.message}")
                        Toast.makeText(context, "Failed to send verification email. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}