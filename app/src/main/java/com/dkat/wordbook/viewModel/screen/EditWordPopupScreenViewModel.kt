package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditWordPopupScreenViewModel : ViewModel() {
    private val _editableWordState = MutableStateFlow(EditableWordState())
    val editableWordState: StateFlow<EditableWordState> = _editableWordState.asStateFlow()

    fun updateEditableWordState(editableWordState: EditableWordState) {
        _editableWordState.value = editableWordState
    }
}

data class EditableWordState(
    val currentSource: Source = Source(),
    val currentWord: Word_B = Word_B(),
    val currentTranslations: List<Translation> = emptyList()
)
