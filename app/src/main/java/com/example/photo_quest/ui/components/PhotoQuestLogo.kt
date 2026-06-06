package com.example.photo_quest.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp

@Composable
fun PhotoQuestLogo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Photo Quest",
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Cursive,
        fontSize = 64.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}