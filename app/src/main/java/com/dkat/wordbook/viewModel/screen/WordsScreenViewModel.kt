package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WordsScreenViewModel : ViewModel() {
    private val _selectedSource = MutableStateFlow(Source())
    val selectedSource: StateFlow<Source> = _selectedSource.asStateFlow()

    fun updateSelectedSource(source: Source) {
        _selectedSource.value = source
    }
}
