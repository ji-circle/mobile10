package com.example.fourthapp.ui

data class GameUiState(
    val currentScrambledWord: String = "",
    //아래 추가
    val currentWordCount: Int = 1,
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0
)