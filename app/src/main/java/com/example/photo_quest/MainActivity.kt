package com.example.photo_quest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.photo_quest.ui.navigation.NavigationRoot
import com.example.photo_quest.ui.navigation.Route
import com.example.photo_quest.ui.theme.PhotoQuestTheme
import com.example.photo_quest.ui.viewmodels.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    lateinit var auth: FirebaseAuth
    val startingScreen = mutableStateOf<Route>(Route.Home)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
//        startingScreen.value = if (auth.currentUser == null) Route.LogIn else Route.Home

        enableEdgeToEdge()
        setContent {
            PhotoQuestTheme {
                Scaffold { innerPadding ->
                    NavigationRoot(
                        startingDestination = startingScreen.value,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        startingScreen.value = if (auth.currentUser == null) Route.LogIn else Route.Home
    }

}
