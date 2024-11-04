package com.example.fourthapp.ui

data class GameUiState(
    val currentScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    //아래 추가
    val score: Int = 0
)