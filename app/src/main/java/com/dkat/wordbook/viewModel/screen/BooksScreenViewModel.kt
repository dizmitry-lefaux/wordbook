package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BooksScreenViewModel : ViewModel() {
    private val _isBooksOpen = MutableStateFlow(true)
    val isBooksOpen: StateFlow<Boolean> = _isBooksOpen.asStateFlow()

    private val _isLanguagesOpen = MutableStateFlow(false)
    val isLanguagesOpen: StateFlow<Boolean> = _isLanguagesOpen.asStateFlow()

    fun openBooks() {
        _isBooksOpen.value = true
        _isLanguagesOpen.value = false
    }

    fun openLanguages() {
        _isBooksOpen.value = false
        _isLanguagesOpen.value = true
    }
}
