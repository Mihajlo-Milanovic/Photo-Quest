package com.example.photo_quest.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.photo_quest.ui.components.PhotoQuestBottomBar
import com.example.photo_quest.ui.screens.HomeScreen
import com.example.photo_quest.ui.screens.SettingsScreen
import com.example.photo_quest.ui.screens.auth.LogInScreen


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    startingDestination: Route = Route.Home
) {
    val backStack = rememberNavBackStack(startingDestination)

    Scaffold(
        bottomBar = {
            PhotoQuestBottomBar(
                modifier = Modifier,
                currentScreen = backStack.last(),
                goToHome = { backStack.add(Route.Home) },
                goToSettings = { backStack.add(Route.Settings) },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .padding(innerPadding),
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when (key) {

                    is Route.Home -> {
                        NavEntry(key) {
                            HomeScreen()
                        }
                    }

                is Route.Settings -> {
                    NavEntry(key) {
                        SettingsScreen()
                    }
                }

                    is Route.LogIn -> {
                        NavEntry(key) {
                            LogInScreen(
                                goToHome = { backStack.add(Route.Home) },
//                            goToSignUp = { backStack.add(Route.SignUp) }
                            )
                        }
                    }

                    else -> error("Unknown NavKey: $key")
                }

            }
        )
    }
}