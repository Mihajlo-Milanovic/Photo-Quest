package com.example.photo_quest.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photo_quest.ui.theme.PhotoQuestTheme

@Composable
fun LogInGreeting(
    modifier: Modifier = Modifier,
    username: String,
    showVerifyEmailMessage: Boolean,
    onResendVerificationEmail: () -> Unit
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text("Hello $username")

        if (showVerifyEmailMessage) {
            Text("Please verify your e-mail")

            TextButton(
                onClick = onResendVerificationEmail,
            ) {
                Text("Resend verification e-mail")
            }
        }
    }
}


@Preview(
    showBackground = true,
)
@Composable
private fun Light() {
    PhotoQuestTheme {
        LogInGreeting(
            username = "Mihajlo",
            showVerifyEmailMessage = true,
            onResendVerificationEmail = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Composable
private fun Dark() {
    PhotoQuestTheme{
        Surface {
            LogInGreeting(
                username = "Mihajlo",
                showVerifyEmailMessage = true,
                onResendVerificationEmail = {}
            )
        }
    }
}