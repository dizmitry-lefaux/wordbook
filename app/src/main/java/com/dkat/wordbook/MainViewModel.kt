package com.dkat.wordbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.WordRepository
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
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
) : ViewModel() {
    val random = Random(0)

    private val _sessionWords = MutableStateFlow<List<Word>>(emptyList())
    val sessionWords: StateFlow<List<Word>> = wordRepository.getSessionWordsFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val sources: StateFlow<List<Source>> = wordRepository.readSources().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val sourcesStrings: StateFlow<List<String>> = wordRepository.readSourcesStrings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val languages: StateFlow<List<Language>> = wordRepository.readLanguages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val words: StateFlow<List<Word>> = wordRepository.getWords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun updateSession() {
        val wordsList = words.value.filter { word: Word ->
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
            val job = launch { wordRepository.resetSession() }
            job.join()
        }
    }

    private fun resetIsInSession() {
        viewModelScope.launch {
            val job = launch { wordRepository.resetIsInSession() }
            job.join()
        }
    }

    private fun startNewSession() {
        updateSession(words.value)
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

    // dkat: TODO
    fun addWord(word: Word) {
        viewModelScope.launch {
            wordRepository.createWord(word)
        }
    }

    // dkat: in progress
    fun addSource(source: Source) {
        viewModelScope.launch {
            wordRepository.createSource(source)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }

    fun migrateSources() {
        viewModelScope.launch {
            wordRepository.migrateSources()
        }
    }

    fun migrateLanguages() {
        viewModelScope.launch {
            wordRepository.migrateLanguages()
        }
    }

    fun migrateWords() {
        viewModelScope.launch {
            wordRepository.migrateWords()
        }
    }

    fun migrateTranslations() {
        viewModelScope.launch {
            wordRepository.migrateTranslations()
        }
    }

    fun deleteSource(source: Source) {
        viewModelScope.launch {
            wordRepository.deleteSource(source)
        }
    }

    fun createSource(source: Source) {
        viewModelScope.launch {
            wordRepository.createSource(source)
        }
    }

    fun createLanguage(language: Language) {
        viewModelScope.launch {
            wordRepository.createLanguage(language)
        }
    }

    fun deleteLanguage(language: Language) {
        viewModelScope.launch {
            wordRepository.deleteLanguage(language)
        }
    }
}

class MainViewModelFactory(
    private val wordRepository: WordRepository
) : ViewModelProvider.Factory {
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
