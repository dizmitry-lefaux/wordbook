package com.dkat.wordbook.viewModel.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.repo.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.stream.Collectors
import kotlin.random.Random

private const val TAG = "MainViewModel"
private const val SESSION_SIZE = 3

@Deprecated(message = "Replace with SessionViewModel, remove Word related structures")
class MainViewModel(
    private val wordsRepository: WordsRepository
) : ViewModel() {
    private val random = Random(0)

    private val _sessionWords = MutableStateFlow<List<Word>>(emptyList())
    val sessionWords: StateFlow<List<Word>> = wordsRepository.getSessionWordsFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val wordsOld: StateFlow<List<Word>> = wordsRepository.getWords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun updateSession() {
        val wordsList = wordsOld.value.filter { word: Word ->
            word.sessionWeight > 0
        }
        updateSession(wordsList)
    }

    fun restartSession() {
        viewModelScope.launch {
            val job1 = launch { resetSession() }
            job1.join()
            val job2 = launch { startNewSession() }
            job2.join()
        }
    }

    private fun resetSession() {
        viewModelScope.launch {
            val job = launch { wordsRepository.resetSession() }
            job.join()
        }
    }

    private fun resetIsInSession() {
        viewModelScope.launch {
            val job = launch { wordsRepository.resetIsInSession() }
            job.join()
        }
    }

    private fun startNewSession() {
        updateSession(wordsOld.value)
    }

    private fun updateSession(wordList: List<Word>) {
        resetIsInSession()
        val sessionWords = mutableMapOf<Word, Float>()
        wordList.forEach {
            val randomSessionWeightMultiplier: Float = random.nextFloat()
            sessionWords[it] = randomSessionWeightMultiplier
        }
        val sortedList = sessionWords.toList().sortedByDescending { it.second }

        val listSize = sortedList.size
        val sessionSize = if (listSize > SESSION_SIZE) SESSION_SIZE else listSize

        val sessionIds: List<Int> = sortedList.take(sessionSize).stream()
            .map { pair -> pair.first }
            .map { words -> words.id }
            .collect(Collectors.toList())

        viewModelScope.launch {
            val job1 = launch { wordsRepository.setIsInSessionForList(sessionIds, true) }
            val job2 = launch { wordsRepository.setSessionWeightForList(sessionIds, 0f) }
            job1.join()
            job2.join()
        }.invokeOnCompletion {
            viewModelScope.launch(Dispatchers.Main) {
                _sessionWords.value = wordsRepository.getSessionWords()
            }
        }
    }
}

class MainViewModelFactory(
    private val wordsRepository: WordsRepository
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(wordsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
