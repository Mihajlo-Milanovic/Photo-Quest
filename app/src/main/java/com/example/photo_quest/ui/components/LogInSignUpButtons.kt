package com.example.photo_quest.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photo_quest.ui.theme.PhotoQuestTheme

@Composable
fun LogInSignUpButtons(
    modifier: Modifier = Modifier,
    showSignUp: Boolean = false,
    onLogInSignUpToggle: () -> Unit,
    onAuthenticate: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {

        Button(
            onClick = onAuthenticate,
            modifier = Modifier.fillMaxWidth(0.7f),
        ) {
            if (showSignUp)
                Text("Sign Up")
            else
                Text("Log In")
        }

        TextButton(
            onClick = onLogInSignUpToggle
        ) {
            if (showSignUp)
                Text("Already have an account?")
            else
                Text("Don't have an account?")
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun Light() {
    var showSignUp by remember { mutableStateOf(false)}

    PhotoQuestTheme {
        Surface {
            LogInSignUpButtons(
                onAuthenticate = {},
                showSignUp = showSignUp,
                onLogInSignUpToggle = { showSignUp = !showSignUp }
            )
        }
    }
}


@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun Dark() {
    var showSignUp by remember { mutableStateOf(false)}

    PhotoQuestTheme {
        Surface {
            LogInSignUpButtons(
                onAuthenticate = {},
                showSignUp = showSignUp,
                onLogInSignUpToggle = { showSignUp = !showSignUp }
            )
        }
    }
}