package com.example.fourthapp.ui

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fourthapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    //후보가 될 수 있는 모든 word를 가지고 있는 set을 하나 설정...
    //by로 하면 읽기 쓰기 모두 가능해져서... private에서는 막는다
    private var _allWords = mutableStateOf<Set<String>>(emptySet())

    //State import 할 땐, runtime 있는 import androidx.compose.runtime.State 이거로
    val allWords: State<Set<String>> = _allWords

    //MutableStateFlow 첫번째꺼.. () 붙는거
    //MutableStateFlow를 통해 GameUiState를 업데이트하는 것을 도움...
    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //지금 현재 word가 무엇인지...
    private lateinit var currentWord: String

    private var usedWords: MutableSet<String> = mutableSetOf()

    //allWords에 word들을 장착...
    //Context는 현재 앱의 상태st...
    // 밖에서도 해야하니까 private이 아님
    fun loadStringSet(context: Context) {
        val stringArray = context.resources.getStringArray(R.array.game_strings)
        //by로 하지 않았기 때문에 .value로 해야 함
        _allWords.value = stringArray.toSet()
    }

    //셔플
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()

        //만약 셔플한 값이 원래 단어랑 동일하면 다시 셔플함
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        //string 형태로 다시 바꿔서 리턴함
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.value.random()

        //만약 contain 되어있다면... 재귀함수 사용
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            //usedWords에 현재 단어를 추가하고
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

}