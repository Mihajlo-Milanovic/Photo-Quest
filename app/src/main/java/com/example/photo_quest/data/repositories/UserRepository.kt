package com.example.photo_quest.data.repositories

import android.net.Uri
import com.example.photo_quest.data.models.User
import com.example.photo_quest.data.sources.RemoteAuthDataSource
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserRepository @Inject constructor(
    val remoteAuthDataSource: RemoteAuthDataSource
) {

    val user = remoteAuthDataSource.user.map {
        it?.let {
            User(
                name = it.displayName ?: "",
                email = it.email ?: "",
                emailVerified = it.isEmailVerified,
                photoUrl = it.photoUrl ?: Uri.EMPTY,
                uid = it.uid
            )
        }
    }

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