package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditLanguagePopupScreenViewModel : ViewModel() {
    private val _editableLanguageState = MutableStateFlow(Language())
    val editableLanguageState: StateFlow<Language> = _editableLanguageState.asStateFlow()

    fun updateEditableLanguageState(language: Language) {
        _editableLanguageState.value = language
    }
}
