package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditSessionPopupScreenViewModel : ViewModel() {
    private val _editableSessionState = MutableStateFlow(EditableSessionState())
    val editableSessionState: StateFlow<EditableSessionState> = _editableSessionState.asStateFlow()

    fun updateEditableSessionState(editableSessionState: EditableSessionState) {
        _editableSessionState.value = editableSessionState
    }
}

data class EditableSessionState(
    val currentSession: Session = Session(),
    val currentSources: List<Source> = emptyList()
)
