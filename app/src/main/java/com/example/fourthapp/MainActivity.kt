package com.example.fourthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fourthapp.ui.GamePage
import com.example.fourthapp.ui.GameViewModel
import com.example.fourthapp.ui.ResultPage
import com.example.fourthapp.ui.theme.FourthAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FourthAppTheme {
                //seventhViewModel... 뷰모델 reset
                val gameViewModel = viewModel<GameViewModel>()
                gameViewModel.loadStringSet(this)
                gameViewModel.resetGame()
                GamePage(gameViewModel = gameViewModel)
//                ResultPage()
            }
        }
    }
}

