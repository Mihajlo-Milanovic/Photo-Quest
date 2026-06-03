package com.example.photo_quest.data.repositories

import android.content.Context
import android.net.Uri
import com.example.photo_quest.data.models.User
import com.example.photo_quest.data.sources.RemoteAuthDataSource
import kotlinx.coroutines.CoroutineScope
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

    fun logIn(email: String, password: String) = remoteAuthDataSource.logIn(email, password)
    fun signUp(email: String, password: String) = remoteAuthDataSource.signUp(email, password)
    fun logOut() = remoteAuthDataSource.logOut()

    suspend fun updateUser(user: User) = remoteAuthDataSource.updateUser(user)
    suspend fun changeEmail(email: String) = remoteAuthDataSource.changeEmail(email)
    fun sendVerificationEmail(context: Context, coroutineScope: CoroutineScope) = remoteAuthDataSource.sendVerificationEmail(context, coroutineScope)
    fun sendPasswordResetEmail(email: String, context: Context) = remoteAuthDataSource.sendPasswordResetEmail(email, context)
}