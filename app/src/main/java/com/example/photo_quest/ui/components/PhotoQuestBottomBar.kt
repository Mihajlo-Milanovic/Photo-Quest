package com.example.photo_quest.ui.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.example.photo_quest.ui.navigation.Route
import com.example.photo_quest.ui.theme.PhotoQuestTheme

@Composable
fun PhotoQuestBottomBar(
    modifier: Modifier = Modifier,
    currentScreen: NavKey,
    goToHome: () -> Unit,
    goToSettings: () -> Unit,
    goToLeaderboard: () -> Unit,
    goToNewQuest: () -> Unit,
    goToSearch: () -> Unit,
) {

    if (currentScreen == Route.LogIn)
        return

    NavigationBar(
        modifier = modifier
    ) {

        NavigationBarItem(
            selected = currentScreen == Route.Leaderboard,
            onClick = goToLeaderboard,
            icon = { Icon(Icons.Default.Leaderboard, contentDescription = "Leaderboard") },
            label = { Text("Score") },
        )

        NavigationBarItem(
            selected = currentScreen == Route.NewQuest,
            onClick = goToNewQuest,
            icon = { Icon(Icons.Default.AddAPhoto, contentDescription = "New quest") },
            label = { Text("New Quest") },
        )

        NavigationBarItem(
            selected = currentScreen == Route.Home,
            onClick = goToHome,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
        )

        NavigationBarItem(
            selected = currentScreen == Route.Search,
            onClick = goToSearch,
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
        )

        NavigationBarItem(
            selected = currentScreen == Route.Settings,
            onClick = goToSettings,
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
        )

    }
}

@Preview
@Composable
private fun Light() {
    PhotoQuestTheme {
        Surface {
            var currentScreen by remember { mutableStateOf<Route>(Route.Home) }
            PhotoQuestBottomBar(
                currentScreen = currentScreen,
                goToHome = { currentScreen = Route.Home },
                goToSettings = { currentScreen = Route.Settings },
                goToLeaderboard = { currentScreen = Route.Leaderboard },
                goToNewQuest = { currentScreen = Route.NewQuest },
                goToSearch = { currentScreen = Route.Search },
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun Dark() {
    PhotoQuestTheme {
        Surface {
            var currentScreen by remember { mutableStateOf<Route>(Route.Home) }
            PhotoQuestBottomBar(
                currentScreen = currentScreen,
                goToHome = { currentScreen = Route.Home },
                goToSettings = { currentScreen = Route.Settings },
                goToLeaderboard = { currentScreen = Route.Leaderboard },
                goToNewQuest = { currentScreen = Route.NewQuest },
                goToSearch = { currentScreen = Route.Search },
            )
        }
    }
}