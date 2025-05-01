package com.dkat.wordbook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.data.WordRepository
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceWithWords
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
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
    private val random = Random(0)

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

    val sourcesWithWords: StateFlow<List<SourceWithWords>> = wordRepository.readSourcesWithWords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val wordsWithTranslations: StateFlow<List<WordWithTranslations>> = wordRepository.readWordsWithTranslations().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val languages: StateFlow<List<Language>> = wordRepository.readLanguages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val wordsOld: StateFlow<List<Word>> = wordRepository.getWords().stateIn(
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

    private suspend fun createWord(word: Word_B): Long {
        var wordId: Long = 0
        viewModelScope.launch { wordId = wordRepository.createWord(word) }.join()
        return wordId
    }

    private suspend fun createTranslation(translation: Translation): Long {
        var translationId: Long = 0
        viewModelScope.launch { translationId = wordRepository.createTranslation(translation) }.join()
        return translationId
    }

    // blocking read
    fun readSource(id: Int): Source {
        return wordRepository.readSource(id)
    }

    fun deleteWord(word: Word_B) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }

    fun deleteSource(source: Source) {
        viewModelScope.launch {
            wordRepository.deleteSource(source)
        }
    }

    fun createSource(source: Source): Long {
        var sourceId: Long = 0
        viewModelScope.launch {
            sourceId = wordRepository.createSource(source)
        }
        return sourceId
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

    fun createWordWithTranslation(word: Word_B, translation: Translation) {
        viewModelScope.launch {
            var wordId = 0
            launch { wordId = createWord(word).toInt() }.join()
            Log.i(TAG, "Word created: $word; id: $wordId")
            val translationUpdated = Translation(
                wordId = wordId,
                value = translation.value,
                languageId = translation.languageId
            )
            var translationId = 0
            launch { translationId = createTranslation(translationUpdated).toInt() }.join()
            Log.i(TAG, "Translation created: $translationUpdated; id: $translationId")
        }
    }

    fun createWordWithTranslations(word: Word_B, translations: List<Translation>) {
        Log.i(TAG, "Creating word: $word")
        Log.i(TAG, "Creating translations: ${translations.joinToString { translation -> translation.toString() }}")
        viewModelScope.launch {
            var wordId = 0
            launch {
                wordId = createWord(word).toInt() }.join()
            Log.i(TAG, "Word created: $word; id: $wordId")
            val translationsUpdated = translations.map {
                Translation(
                    wordId = wordId,
                    value = it.value,
                    languageId = it.languageId
                )
            }.toList()
            translationsUpdated.forEach {
                var translationId = 0
                launch { translationId = createTranslation(it).toInt() }.join()
                Log.i(TAG, "Translation created: $translationId; id: $translationId")
            }
        }
    }
}

class MainViewModelFactory(
    private val wordRepository: WordRepository
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(wordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
