package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private val _uiState = MutableStateFlow(GameUiState())
val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
class GameViewModel : ViewModel() {
    init {
        resetGame()
    }
}
private var _count = 0
val count: Int
    get() = _count
private lateinit var currentWord: String
private var usedWords: MutableSet<String> = mutableSetOf()
private fun pickRandomWordAndShuffle(): String {
    //continue picking up random words until you get one that hasn't been used
    currentWord = allWords.random()
    if (usedWords.contains(currentWord)) {
        return pickRandomWordAndShuffle()
    } else {
        usedWords.add(currentWord)
        return shuffleCurrentWord(currentWord)
    }
}
private fun shuffleCurrentWord(word: String): String {
    val tempWord = word.toCharArray()
    //Scramble the word
    tempWord.shuffle()
    while (String(tempWord).equals(word)) {
        tempWord.shuffle()
    }
    return String(tempWord)

}

fun resetGAme() {
    usedWords.clear()
    _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
}