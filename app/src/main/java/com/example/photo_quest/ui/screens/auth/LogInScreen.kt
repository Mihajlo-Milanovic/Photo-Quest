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
    goToSignUp: () -> Unit,
    viewModel: LogInScreenViewModel = viewModel()
) {

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ){

        Text(
            text = "Photo Quest",
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            fontSize = 64.sp,
            color = MaterialTheme.colorScheme.primary,
        )

        Column(
            verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//                .padding(32.dp)
                .fillMaxWidth()
//                .fillMaxHeight(0.5f),

        ) {
            OutlinedTextField(
                value = viewModel.emailOrPhone,
                onValueChange = {
                    viewModel.emailOrPhone = it
                },
                label = { Text("E-mail or Phone") },
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

        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = goToHome,
                modifier = Modifier.fillMaxWidth(0.7f),
            ) {
                Text("Log In")
            }

            TextButton(
                onClick = goToSignUp,
            ) {
                Text("Don't have an account?")
            }
        }
    }
}