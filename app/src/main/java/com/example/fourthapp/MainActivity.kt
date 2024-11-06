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

//TODO 질문 1 - [gamePage] 공식문서에서는 여기서 mutableStateOf(false) 라고 적혀있던데...

//TODO 질문 2 - [viewModel]이전 단어들을 따로 저장하는 게 아니라, 넘어가기 전에 저장하고 넘어가고 싶음

//TODO 질문 3 - [wordsData] 이건 안쓰는건가?

//TODO 질문 4 - [gameUiState] state에는 상태를 넣고, viewmodel에 데이터를 넣는거라고 이해...근데 state에 score이 있는데? 차이...

//TODO 질문 5 - [resultPage] set으로 저장한 것들 돌리려고 iterator 사용... 다른 방법은?