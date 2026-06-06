package com.example.photo_quest.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photo_quest.ui.theme.PhotoQuestTheme

@Composable
fun PasswordRequirements(
    modifier: Modifier = Modifier
) {
    Text(
        text = """ 
                                Password must contain:
                                one uppercase letter    [A-Z],
                                one lowercase letter    [a-z],
                                one number              [0-9],
                                special character       
                                [e.g. !, @, #, $, %, &, *]
                            """.trimIndent(),
        style = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onError,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
        ),
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
            .padding(32.dp)

    )
}

@Preview(
    showBackground = true
)
@Composable
private fun PasswordRequirementsPreviewLight() {
    PhotoQuestTheme {
        Surface{
            PasswordRequirements()
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun PasswordRequirementsPreviewDark() {
    PhotoQuestTheme {
        Surface {
            PasswordRequirements()
        }
    }
}