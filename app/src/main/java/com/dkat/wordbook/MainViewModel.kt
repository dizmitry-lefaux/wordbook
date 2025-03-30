package com.dkat.wordbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.Word
import com.dkat.wordbook.data.WordRepository
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

class MainViewModel(
    private val wordRepository: WordRepository
) : ViewModel()
{
    val random = Random(0)

    private val _selectedSource = MutableStateFlow<String>("")
    val selectedSource: StateFlow<String> = _selectedSource

    private val _selectedSourceWords = MutableStateFlow<List<Word>>(emptyList())
    val selectedSourceWords: StateFlow<List<Word>> = _selectedSourceWords

    private val _sessionWords = MutableStateFlow<List<Word>>(emptyList())
    val sessionWords: StateFlow<List<Word>> = wordRepository.getSessionWordsFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val sources: StateFlow<List<String>> = wordRepository.getSources().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val words: StateFlow<List<Word>> = wordRepository.getWords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun selectSource(sourceName: String)
    {
        _selectedSource.value = sourceName
        Log.i(TAG, "Selected source: $sourceName")

        // using suspend function returning not flow direct value
        viewModelScope.launch(Dispatchers.Main) {
            _selectedSourceWords.value = wordRepository.getSourceWords(sourceName = sourceName)
        }.invokeOnCompletion(handler = {
            selectedSourceWords.value.forEach { words ->
                Log.i(TAG, "Words: $words")
            }
        })
    }

    fun updateSession()
    {
        val wordsList = words.value.filter { word: Word ->
            word.sessionWeight > 0
        }
        updateSession(wordsList)
    }

    fun restartSession()
    {
        viewModelScope.launch {
            val job1 = launch { resetSession() }
            job1.join()
            val job2 = launch { startNewSession() }
            job2.join()
        }
    }

    private fun resetSession()
    {
        viewModelScope.launch {
            val job = launch { wordRepository.resetSession() }
            job.join()
        }
    }

    private fun resetIsInSession()
    {
        viewModelScope.launch {
            val job = launch { wordRepository.resetIsInSession() }
            job.join()
        }
    }

    private fun startNewSession()
    {
        updateSession(words.value)
    }

    private fun updateSession(wordList: List<Word>)
    {
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
            val job1 = launch { wordRepository.setIsInSessionForList(sessionIds, true) }
            val job2 = launch { wordRepository.setSessionWeightForList(sessionIds, 0f) }
            job1.join()
            job2.join()
        }.invokeOnCompletion {
            viewModelScope.launch(Dispatchers.Main) {
                _sessionWords.value = wordRepository.getSessionWords()
            }
        }
    }

    fun addWord(word: Word)
    {
        viewModelScope.launch {
            wordRepository.storeDataInDB(word)
        }
    }

    fun deleteWord(word: Word)
    {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }
}

class MainViewModelFactory(
    private val wordRepository: WordRepository
) : ViewModelProvider.Factory
{
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
        {
            return MainViewModel(wordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
