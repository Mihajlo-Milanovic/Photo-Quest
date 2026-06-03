package com.example.photo_quest.data.models

import android.net.Uri
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest

data class User (
    val name: String,
    val email: String,
    val photoUrl: Uri,
    val emailVerified: Boolean,
        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getIdToken() instead.
    val uid: String
)