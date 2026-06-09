package com.example.photo_quest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.photo_quest.ui.viewmodels.NewQuestScreenViewModel

@Composable
fun NewQuestScreen(
    modifier: Modifier = Modifier,
    viewModel: NewQuestScreenViewModel
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Add quest",
        )

        Button(
            onClick = viewModel::getImageUri
        ) {
            Text("Get image")
        }

        AsyncImage(
            model = viewModel.imageUri,
//            ImageRequest.Builder(LocalContext.current)
//                .data(viewModel.imageUri)
//                .crossfade(true)
//                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
        )


    }
}