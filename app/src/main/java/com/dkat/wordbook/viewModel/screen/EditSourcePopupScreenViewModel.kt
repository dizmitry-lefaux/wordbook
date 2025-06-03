package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditSourcePopupScreenViewModel : ViewModel() {
    private val _editableSourceState = MutableStateFlow(Source())
    val editableSourceState: StateFlow<Source> = _editableSourceState.asStateFlow()

    fun updateEditableSourceState(source: Source) {
        _editableSourceState.value = source
    }
}
