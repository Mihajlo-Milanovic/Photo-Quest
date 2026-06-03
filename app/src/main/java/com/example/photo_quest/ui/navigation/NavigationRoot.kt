package com.example.photo_quest.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.photo_quest.ui.components.PhotoQuestBottomBar
import com.example.photo_quest.ui.screens.HomeScreen
import com.example.photo_quest.ui.screens.SettingsScreen
import com.example.photo_quest.ui.screens.auth.LogInScreen
import com.example.photo_quest.ui.viewmodels.HomeScreenViewModel
import com.example.photo_quest.ui.viewmodels.SettingsScreenViewModel
import com.example.photo_quest.ui.viewmodels.auth.LogInScreenViewModel


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(Route.LogIn)

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
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = { key ->
                when (key) {

                    is Route.Home -> {
                        NavEntry(key) {
                            val viewModel = hiltViewModel<HomeScreenViewModel>()
                            HomeScreen(
                                viewModel = viewModel
                            )
                        }
                    }

                    is Route.Settings -> {
                        NavEntry(key) {
                            val viewModel = hiltViewModel<SettingsScreenViewModel>()
                            SettingsScreen(
                                viewModel = viewModel
                            )
                        }
                    }

                    is Route.LogIn -> {
                        NavEntry(key) {
                            val viewModel = hiltViewModel<LogInScreenViewModel>()
                            LogInScreen(
                                goToHome = {
                                    backStack.remove(Route.LogIn)
                                    backStack.add(Route.Home)
                                },
                                viewModel = viewModel
                            )
                        }
                    }

                    else -> error("Unknown NavKey: $key")
                }

            }
        )
    }
}