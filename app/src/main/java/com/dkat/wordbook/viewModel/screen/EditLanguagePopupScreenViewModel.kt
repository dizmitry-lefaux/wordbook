package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditLanguagePopupScreenViewModel : ViewModel() {
    private val _editLanguageState = MutableStateFlow(Language())
    val editLanguageState: StateFlow<Language> = _editLanguageState.asStateFlow()

    fun updateEditLanguageState(language: Language) {
        _editLanguageState.value = language
    }
}
