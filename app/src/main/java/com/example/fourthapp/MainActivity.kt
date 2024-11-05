package com.example.fourthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                val gameViewModel = viewModel<GameViewModel>()
                gameViewModel.loadStringSet(this)
                gameViewModel.resetGame()

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "game") {
                    composable(route = "game") {
                        GamePage(
                            gameViewModel = gameViewModel,
                            checkScore = { navController.navigate(route = "result") }
                        )
                    }
                    composable(route = "result") {
                        ResultPage(
                            gameViewModel = gameViewModel,
                            returnToGame = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}

//TODO wordsData는 안쓰는건가???
//TODO state에는 상태를 넣는데, viewmodel에 데이터를 넣는 거랑 뭐가 다른거지...? state에 score이 있는데??
//TODO gameviewmodel에서... 질문