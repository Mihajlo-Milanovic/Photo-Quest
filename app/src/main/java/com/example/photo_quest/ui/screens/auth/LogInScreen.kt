package com.example.photo_quest.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            if (it.emailVerified)
                goToHome()
            else {
                Toast.makeText(context, "Verify your e-mail", Toast.LENGTH_SHORT).show()
                viewModel.email = it.email
                viewModel.showResendVerificationEmail = true
            }
        }
    }

    if (viewModel.showResetPasswordDialog)
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
                    onClick = { viewModel.resetPasswordResetEmail(context = context) }
                ) {
                    Text("Send e-mail")
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

        Text(
            text = "Photo Quest",
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            fontSize = 64.sp,
            color = MaterialTheme.colorScheme.primary,
        )

        if (viewModel.loading) {
            CircularProgressIndicator()
        } else {
            Column(
                verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
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
                    )
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
                    },
                    singleLine = true,

                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        imeAction = if (viewModel.showSignUp) ImeAction.Next else ImeAction.Done,
                        showKeyboardOnFocus = true,
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Password,
                    )
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
                                    Icon(Icons.Default.VisibilityOff, contentDescription = "Hide password")
                                else
                                    Icon(Icons.Default.Visibility, contentDescription = "Show password")
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Done,
                            showKeyboardOnFocus = true,
                            capitalization = KeyboardCapitalization.None,
                            keyboardType = KeyboardType.Password,
                        )
                    )

                    if (viewModel.showResendVerificationEmail)
                        TextButton(
                            onClick = { viewModel.sendVerificationEmail(context = context) },
                        ) {
                            Text("Resend verification e-mail")
                        }
                } else if (viewModel.showResetPassword)
                    TextButton(
                        onClick = { viewModel.showResetPasswordDialog = true },
                    ) {
                        Text("Forgot password?")
                    }
                else
                    Spacer(modifier = Modifier.height(64.dp))
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

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