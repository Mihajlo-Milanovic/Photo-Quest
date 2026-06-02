package com.example.photo_quest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.example.photo_quest.ui.navigation.Route

@Composable
fun PhotoQuestBottomBar(
    modifier: Modifier = Modifier,
    currentScreen: NavKey,
    goToHome: () -> Unit,
    goToSettings: () -> Unit,
) {
    NavigationBar(
        modifier = modifier
    ) {

        NavigationBarItem(
            selected = currentScreen == Route.Home,
            onClick = goToHome,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
        )

        NavigationBarItem(
            selected = currentScreen == Route.Settings,
            onClick = goToSettings,
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
        )

    }
}