package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditSourcePopupScreenViewModel : ViewModel() {
    private val _editSourceState = MutableStateFlow(Source())
    val editSourceState: StateFlow<Source> = _editSourceState.asStateFlow()

    fun updateEditSourceState(source: Source) {
        _editSourceState.value = source
    }
}
