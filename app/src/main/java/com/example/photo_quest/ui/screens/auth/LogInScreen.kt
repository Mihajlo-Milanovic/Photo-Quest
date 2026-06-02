package com.example.photo_quest.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photo_quest.ui.viewmodels.auth.LogInScreenViewModel

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    goToHome: () -> Unit,
    viewModel: LogInScreenViewModel = viewModel()
) {

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {

        Text(
            text = "Photo Quest",
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            fontSize = 64.sp,
            color = MaterialTheme.colorScheme.primary,
        )

        if (viewModel.loading) {
            CircularProgressIndicator()
        }
        else {
            Column(
                verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
//                .padding(32.dp)
                    .fillMaxWidth()
//                .fillMaxHeight(0.5f),

            ) {
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = {
                        viewModel.email = it
                    },
                    label = { Text("E-mail") },
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
                                Icon(Icons.Default.VisibilityOff, contentDescription = "Hide password")
                            else
                                Icon(Icons.Default.Visibility, contentDescription = "Show password")
                        }
                    }
                )

                if (viewModel.showSignUp)
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
                                    Icon(Icons.Default.VisibilityOff, contentDescription = "Hide password")
                                else
                                    Icon(Icons.Default.Visibility, contentDescription = "Show password")
                            }
                        }
                    )
                else if (viewModel.showResetPassword)
                    TextButton(
                        onClick = { viewModel.showSignUp = !viewModel.showSignUp },
                    ) {
                        Text("Forgot password?")
                    }
                else
                    Spacer(modifier = Modifier.height(64.dp))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        viewModel.authenticate(
                            context = context,
                            goToHome = goToHome
                        )
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                ) {
                    if (viewModel.showSignUp)
                        Text("Sign Up")
                    else
                        Text("Log In")
                }

                TextButton(
                    onClick = { viewModel.showSignUp = !viewModel.showSignUp },
                ) {
                    if (viewModel.showSignUp)
                        Text("Already have an account?")
                    else
                        Text("Don't have an account?")
                }
            }
        }
    }
}