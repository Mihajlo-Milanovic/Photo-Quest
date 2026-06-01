package com.example.photo_quest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.photo_quest.ui.navigation.NavigationRoot
import com.example.photo_quest.ui.theme.PhotoQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoQuestTheme {
                Scaffold { innerPadding ->
                    NavigationRoot(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PhotoQuestTheme {
//        Greeting("Android")
//    }
//}