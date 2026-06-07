package com.example.photo_quest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ResetPasswordDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    email: String
) {
    Dialog(
        onDismissRequest = onDismiss
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
//
//            OutlinedTextField(
//                value = viewModel.email,
//                onValueChange = {
//                    viewModel.email = it
//                },
//                label = { Text("E-mail") },
//                singleLine = true,
//            )
//
//            val context = LocalContext.current
//            Button(
//                onClick = { viewModel.resetPasswordResetEmail(context = context) }
//            ) {
//                Text("Send e-mail")
//            }
        }
    }
}