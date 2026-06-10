package com.example.photo_quest.data.repositories

import android.util.Log
import com.example.photo_quest.data.models.User
import com.example.photo_quest.data.sources.RemoteAuthDataSource
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserRepository @Inject constructor(
    val remoteAuthDataSource: RemoteAuthDataSource
) {

    val user = remoteAuthDataSource.user.map { firebaseUser ->
        firebaseUser?.let {
             User(firebaseUser).copy()
        }.also {
            Log.d("AUTH::CURRENT_USER", it.toString())
        }
    }

    fun currentUser() = remoteAuthDataSource.currentUser()?.let { User(it) }

    suspend fun reloadUser() = remoteAuthDataSource.reloadUser()

    suspend fun logIn(email: String, password: String) = remoteAuthDataSource.logIn(email, password)

    suspend fun signUp(email: String, password: String) = remoteAuthDataSource.signUp(email, password)

    fun logOut() = remoteAuthDataSource.logOut()

    suspend fun updateUser(user: User): Boolean {

        val profileUpdates = userProfileChangeRequest {
            displayName = user.name
            photoUri = user.photoUrl
        }
        return remoteAuthDataSource.updateUser(profileUpdates)
    }

    suspend fun changeEmail(email: String) = remoteAuthDataSource.changeEmail(email)

    suspend fun sendPasswordResetEmail(email: String) = remoteAuthDataSource.sendPasswordResetEmail(email)
    suspend fun sendVerificationEmail() = remoteAuthDataSource.sendVerificationEmail()
}