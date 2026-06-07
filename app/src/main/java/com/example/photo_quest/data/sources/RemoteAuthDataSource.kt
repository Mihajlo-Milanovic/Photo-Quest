package com.example.photo_quest.data.sources

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteAuthDataSource @Inject constructor() {

    private val _user = MutableStateFlow(Firebase.auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user.asStateFlow()

    init {
        Firebase.auth.addAuthStateListener {
            _user.value = it.currentUser
        }
    }

    suspend fun reloadUser() {
        Firebase.auth.currentUser?.reload()?.await()
        _user.value = null
        _user.value = Firebase.auth.currentUser
        Log.d("AUTH::USER_RELOAD", Firebase.auth.currentUser.toString())
    }

//    val user = callbackFlow {
//
//        val listener = FirebaseAuth.AuthStateListener { auth ->
//
//            Log.d(
//                "AUTH::STATE", "onAuthStateChanged: ${
//                    auth.currentUser?.let {
//                        User(
//                            name = it.displayName ?: "",
//                            email = it.email ?: "",
//                            photoUrl = it.photoUrl ?: Uri.EMPTY,
//                            emailVerified = it.isEmailVerified,
//                            uid = it.uid
//                        )
//                    } ?: "NULL"
//                }")
//            trySend(auth.currentUser)
//        }
//
//        Firebase.auth.addAuthStateListener(listener)
//
//        awaitClose {
//            Firebase.auth.removeAuthStateListener(listener)
//        }
//    }

    suspend fun logIn(email: String, password: String): String =
        try {
            Firebase.auth.signOut()
            Firebase.auth.signInWithEmailAndPassword(email, password).await().user?.let {
                return if (it.isEmailVerified) {
                    Log.d("AUTH::LOG_IN", "signInWithEmail:success")
                    "Log in successful"
                }
                else{
                    Log.d("AUTH::LOG_IN", "signInWithEmail:failure \t E-mail not verified")
                    Firebase.auth.signOut()
                    "E-mail not verified"
                }
            }
            "Wrong e-mail or password"
        } catch (ex: Exception) {
            Log.e("AUTH::LOG_IN", "signInWithEmail:failure", ex)
            "Invalid credentials"
        }

    suspend fun signUp(email: String, password: String): AuthResult? {
        var result: AuthResult? = null
        try {
            result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Log.d("AUTH::SIGN_UP", "createUserWithEmail:success")
        } catch (ex: Exception) {
            Log.e("AUTH::SIGN_UP", "createUserWithEmail:failure", ex)
        }
        return result
    }

    fun logOut() = try {
        Firebase.auth.signOut()
        Log.d("AUTH::LOGOUT", "User signed out.")
    } catch (ex: Exception) {
        Log.e("AUTH::LOGOUT", "Error signing out", ex)
    }

    suspend fun updateUser(profileUpdates: UserProfileChangeRequest): Boolean = try {
        Firebase.auth.currentUser?.let {
            it.updateProfile(profileUpdates).await()
            Log.d("AUTH::USER_UPDATE", "User profile updated.")
            reloadUser()
            return true
        }
        return false
    } catch (ex: Exception) {
        Log.e("AUTH::USER_UPDATE", "Error updating user profile", ex)
        return false
    }

    suspend fun changeEmail(email: String): Boolean = try {
        Firebase.auth.currentUser?.let{
            it.verifyBeforeUpdateEmail(email).await()
            Log.d("AUTH::E-MAIL_CHANGE", "User email address updated.")
            reloadUser()
            return true
        }
        return false
    } catch (ex: Exception) {
        Log.e("AUTH::E-MAIL_CHANGE", "Error updating user email", ex)
        return false
    }

    suspend fun sendPasswordResetEmail(email: String): Boolean = try {
        Firebase.auth.useAppLanguage()
        Firebase.auth.sendPasswordResetEmail(email).await()
        Log.d("AUTH::RESET_E-MAIL", "Password reset e-mail sent.")
        return true
    } catch (ex: Exception) {
        Log.e("AUTH::RESET_E-MAIL", "Error while sending password reset e-mail.", ex)
        return false
    }

    suspend fun sendVerificationEmail(): Boolean = try {
        Firebase.auth.useAppLanguage()
        Firebase.auth.currentUser?.let {
            it.sendEmailVerification().await()
            Log.d("AUTH::VERIFICATION_EMAIL", "Verification e-mail sent to ${it.email}")
            return true
        }
        return false
    } catch (ex: Exception) {
        Log.e("AUTH::VERIFICATION_EMAIL", "Failed to send verification email", ex)
        return false
    }


}


