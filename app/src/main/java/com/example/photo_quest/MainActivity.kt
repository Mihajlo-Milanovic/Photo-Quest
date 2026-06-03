package com.example.photo_quest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.photo_quest.data.repositories.UserRepository
import com.example.photo_quest.ui.navigation.NavigationRoot
import com.example.photo_quest.ui.navigation.Route
import com.example.photo_quest.ui.theme.PhotoQuestTheme
import com.example.photo_quest.ui.viewmodels.auth.LogInScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            PhotoQuestTheme {

                NavigationRoot()
            }
        }
    }


}
