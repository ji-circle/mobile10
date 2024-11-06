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
)