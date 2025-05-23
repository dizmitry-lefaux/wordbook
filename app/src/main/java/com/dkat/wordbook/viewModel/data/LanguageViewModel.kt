package com.dkat.wordbook.viewModel.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.repo.LanguageRepository
import com.dkat.wordbook.data.entity.Language
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "LanguageViewModel"

class LanguageViewModel(
    private val languageRepository: LanguageRepository
) : ViewModel() {
    fun createLanguage(language: Language) {
        viewModelScope.launch {
            languageRepository.createLanguage(language)
        }
    }

    val languages: StateFlow<List<Language>> = languageRepository.readLanguages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun deleteLanguage(language: Language) {
        viewModelScope.launch {
            languageRepository.deleteLanguage(language)
        }
    }

    fun updateLanguage(language: Language) {
        viewModelScope.launch {
            launch { updateLanguagePrivate(language) }.join()
        }
        Log.i(TAG, "Language updated: $language")
    }

    private suspend fun updateLanguagePrivate(language: Language) {
        viewModelScope.launch {
            languageRepository.updateLanguage(language)
        }
    }
}

class LanguageViewModelFactory(
    private val languageRepository: LanguageRepository
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
            return LanguageViewModel(languageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
