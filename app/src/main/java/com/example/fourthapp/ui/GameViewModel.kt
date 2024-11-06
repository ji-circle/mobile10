package com.example.fourthapp.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fourthapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private var _allWords = mutableStateOf<Set<String>>(emptySet())

    val allWords: State<Set<String>> = _allWords

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String

    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    //여기 추가
    private var _highlightWords: MutableSet<String> = mutableSetOf()
    val highlightWords: Set<String> = _highlightWords

//    private var _highlightWithShuffled: MutableSet<String> = mutableSetOf()
//    val highlightWithShuffled: Set<String> = _highlightWithShuffled

    private var _highlightShuffled: MutableSet<String> = mutableSetOf()
    val highlightShuffled: Set<String> = _highlightShuffled

    private lateinit var prevWord: String
    private lateinit var prevShuffle: String


    fun loadStringSet(context: Context) {
        val stringArray = context.resources.getStringArray(R.array.game_strings)
        _allWords.value = stringArray.toSet()
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()

        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.value.random()

        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            Log.d("셔플전", currentWord)

            return shuffleCurrentWord(currentWord)
        }
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == 10) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {

            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    isMoreThan7 = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc()
                )
            }

        }
    }

    fun addHighlightWords() {
//        _highlightWithShuffled.add("$prevShuffle to $prevWord")
        _highlightWords.add(prevWord)
        _highlightShuffled.add(prevShuffle)

        val updatedScore = _uiState.value.score.plus(10)
        _uiState.update { currentState ->
            currentState.copy(
                highlightNum = currentState.highlightNum.inc(),
            )
        }
        updateGameState(updatedScore)
    }

    fun notAddHighlightWords(){
        val updatedScore = _uiState.value.score.plus(10)
        _uiState.update { currentState ->
            currentState.copy(
                highlightNum = currentState.highlightNum.inc(),
            )
        }
        updateGameState(updatedScore)
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
//            val updatedScore = _uiState.value.score.plus(10)
            //여기 추가?
            if (checkOver7(currentWord)) {
                prevWord = currentWord
                prevShuffle = uiState.value.currentScrambledWord

                _uiState.update { currentState ->
                    currentState.copy(
                        isMoreThan7 = true,
                        isGuessedWordWrong = false,
                        isHighlightExists = true,
//                        score = updatedScore
                    )
                }
                Log.d("셔플전 아닌 isMoreThan7", uiState.value.isMoreThan7.toString())

            } else {
                val updatedScore = _uiState.value.score.plus(10)

                _uiState.update { currentState ->
                    currentState.copy(
                        isMoreThan7 = false, //
                    )
                }
                updateGameState(updatedScore)
            }
//            updateGameState(updatedScore)

        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = true,
                )
            }
        }
        updateUserGuess("")
    }

    fun skipWord() {
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    fun resetGame() {
        usedWords.clear()
        _highlightWords.clear()
        _highlightShuffled.clear()
//        _highlightWithShuffled.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    //여기 추가
    private fun checkOver7(currentWord: String): Boolean {
        if (currentWord.length >= 7) {
            return true
        } else {
            return false
        }
    }
}