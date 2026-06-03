package com.example.photo_quest.ui.screens.auth

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
import androidx.compose.ui.text.style.TextAlign
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

    LaunchedEffect(user, user?.emailVerified) {

        user?.let {
            if (it.emailVerified) {
                goToHome()
                viewModel.loading = false
            }
            else {
                viewModel.email = it.email
                viewModel.loading = true
                viewModel.showVerifyEmailMessage = true
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
            Column(
                verticalArrangement = spacedBy(128.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CircularProgressIndicator()

                if (viewModel.showVerifyEmailMessage) {
                    Text("Please verify your e-mail")

                    TextButton(
                        onClick = { viewModel.sendVerificationEmail(context = context) },
                    ) {
                        Text("Resend verification e-mail")
                    }
                }
            }
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
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (viewModel.showPasswordRequirements)
                        Text(
                            text = """ 
                                Password must contain
                                one uppercase letter    [A-Z],
                                one lowercase letter    [a-z],
                                one number              [0-9],
                                special character       
                                [e.g. !, @, #, $, %, &, *]
                            """.trimIndent(),
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                            ),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.errorContainer)
                                .padding(32.dp)

                        )

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