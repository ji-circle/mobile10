package com.example.fourthapp.ui

data class GameUiState(
    val currentScrambledWord: String = "",
    val currentWordCount: Int = 1,
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    //여기 추가
    val isMoreThan7: Boolean = false,
    val isHighlightExists: Boolean = false,
    val highlightNum: Int = 0,
    val isConfirm: Boolean = false
    //TODO 질문 4 - [gameUiState] state에는 상태를 넣고, viewmodel에 데이터를 넣는거라고 이해...근데 state에 score이 있는데? 차이...
)