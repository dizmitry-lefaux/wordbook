package com.dkat.wordbook.viewModel.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.data.repo.TranslationRepository
import com.dkat.wordbook.data.repo.WordRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "WordViewModel"

class WordViewModel(
    private val translationRepository: TranslationRepository,
    private val wordRepository: WordRepository
) : ViewModel() {

    val wordsWithTranslations: StateFlow<List<WordWithTranslations>> = wordRepository.readWordsWithTranslations().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private suspend fun createWord(word: Word_B): Long {
        var wordId: Long = 0
        viewModelScope.launch { wordId = wordRepository.createWord(word) }.join()
        return wordId
    }

    private suspend fun updateWord(word: Word_B) {
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }.join()
    }

    private suspend fun createTranslation(translation: Translation): Long {
        var translationId: Long = 0
        viewModelScope.launch { translationId = translationRepository.createTranslation(translation) }.join()
        return translationId
    }

    fun deleteWord(word: Word_B) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }

    private fun deleteTranslationsByWordId(word: Word_B) {
        viewModelScope.launch {
            translationRepository.deleteTranslationsByWordId(word)
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

    fun updateWordWithTranslations(word: Word_B, translations: List<Translation>) {
        Log.i(TAG, "Updating word: $word")
        Log.i(TAG, "Updating translations: ${translations.joinToString { translation -> translation.toString() }}")
        viewModelScope.launch {
            launch {
                updateWord(word)
            }.join()
            Log.i(TAG, "Word updated: $word")

            launch {
                deleteTranslationsByWordId(word)
            }.join()
            Log.i(TAG, "Translations deleted: ${translations.joinToString { translation -> translation.toString() }}")

            val translationsUpdated = translations.map {
                Translation(
                    wordId = word.id,
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

class WordViewModelFactory(
    private val translationRepository: TranslationRepository,
    private val wordRepository: WordRepository
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(translationRepository, wordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
