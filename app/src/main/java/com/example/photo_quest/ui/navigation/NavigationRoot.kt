package com.example.photo_quest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.photo_quest.ui.screens.HomeScreen
import com.example.photo_quest.ui.screens.auth.LogInScreen
import com.example.photo_quest.ui.screens.auth.SignUpScreen


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    startingDestination: Route = Route.Home
) {
    val backStack = rememberNavBackStack(startingDestination)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key){

                is Route.Home -> {
                    NavEntry(key) {
                        HomeScreen()
                    }
                }

                is Route.SignUp -> {
                    NavEntry(key) {
                        SignUpScreen()
                    }
                }

                is Route.LogIn -> {
                    NavEntry(key) {
                        LogInScreen(
                            goToHome = { backStack.add(Route.Home) }
                        )
                    }
                }

                else -> error("Unknown NavKey: $key")
            }

        }
    )
}