package com.example.photo_quest.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.photo_quest.ui.components.LogInGreeting
import com.example.photo_quest.ui.components.LogInSignUpButtons
import com.example.photo_quest.ui.components.PasswordRequirements
import com.example.photo_quest.ui.components.PhotoQuestLogo
import com.example.photo_quest.ui.viewmodels.auth.LogInScreenViewModel

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    goToHome: () -> Unit,
    viewModel: LogInScreenViewModel //= viewModel()
) {
    val context = LocalContext.current
    val user by viewModel.user.collectAsStateWithLifecycle()

    LaunchedEffect(user) {

        user?.let {

            viewModel.showGreeting = true

            if (it.emailVerified) {
                goToHome()
                viewModel.loading = false
            } else {
                viewModel.showVerifyEmailMessage = true
                viewModel.showResendVerificationEmail = true
            }
        }
    }

    if (viewModel.showResetPasswordDialog && user != null) {
        Dialog(
            onDismissRequest = { viewModel.showResetPasswordDialog = false },
        ) {
            Column(
                verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                Text("Enter your e-mail to reset your password")

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = {
                        viewModel.email = it
                    },
                    label = { Text("E-mail") },
                    singleLine = true,
                )

                val context = LocalContext.current
                Button(
                    onClick = { viewModel.sendPasswordResetEmail(context = context, user!!.email) }
                ) {
                    Text("Send e-mail")
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {

        PhotoQuestLogo()

        if (viewModel.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (viewModel.showGreeting) {
                    //User has an account with unverified e-mail address
                    user?.let {
                        LogInGreeting(
//                            modifier = TODO(),
                            username = it.name,
                            showVerifyEmailMessage = viewModel.showVerifyEmailMessage,
                            onResendVerificationEmail = { viewModel.sendVerificationEmail(context) },
                        )
                    }
                } else {
                    // Combined Log in and Sign in forms

                    if (viewModel.showSignUp)
                    OutlinedTextField(
                        value = viewModel.username,
                        onValueChange = {
                            viewModel.username = it
                        },
                        label = { Text("Username") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = true,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Text,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = {
                            viewModel.email = it
                        },
                        label = { Text("E-mail") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = true,
                            imeAction = ImeAction.Next,
                            showKeyboardOnFocus = true,
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Email,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = viewModel.password,
                        onValueChange = {
                            viewModel.password = it
                        },
                        label = { Text("Password") },
                        visualTransformation = if (viewModel.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.showPassword = !viewModel.showPassword }
                            ) {
                                if (viewModel.showPassword)
                                    Icon(
                                        Icons.Default.VisibilityOff,
                                        contentDescription = "Hide password"
                                    )
                                else
                                    Icon(
                                        Icons.Default.Visibility,
                                        contentDescription = "Show password"
                                    )
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = false,
                            imeAction = if (viewModel.showSignUp) ImeAction.Next else ImeAction.Done,
                            showKeyboardOnFocus = true,
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Password,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (viewModel.showSignUp) {
                        OutlinedTextField(
                            value = viewModel.password2,
                            onValueChange = {
                                viewModel.password2 = it
                            },
                            label = { Text("Repeat password") },
                            visualTransformation = if (viewModel.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.showPassword = !viewModel.showPassword }
                                ) {
                                    if (viewModel.showPassword)
                                        Icon(
                                            Icons.Default.VisibilityOff,
                                            contentDescription = "Hide password"
                                        )
                                    else
                                        Icon(
                                            Icons.Default.Visibility,
                                            contentDescription = "Show password"
                                        )
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                autoCorrectEnabled = false,
                                imeAction = ImeAction.Done,
                                showKeyboardOnFocus = true,
                                capitalization = KeyboardCapitalization.None,
                                keyboardType = KeyboardType.Password,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (viewModel.showPasswordRequirements)
                            PasswordRequirements()


                    } else if (viewModel.showResetPassword)
                        TextButton(
                            onClick = { viewModel.showResetPasswordDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Forgot password?")
                        }
                    else
                        Spacer(modifier = Modifier.height(64.dp))
                }
            }

            LogInSignUpButtons(
                modifier = Modifier,
                onAuthenticate = {
                    viewModel.authenticate(
                        context = context,
                        goToHome = goToHome
                    )
                },
                showSignUp = viewModel.showSignUp,
                onLogInSignUpToggle = { viewModel.showSignUp = !viewModel.showSignUp }
            )
        }
    }
}