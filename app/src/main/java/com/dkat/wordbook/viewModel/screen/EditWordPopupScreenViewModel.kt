package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditWordViewModel : ViewModel() {
    private val _editWordState = MutableStateFlow(EditWordState())
    val editWordState: StateFlow<EditWordState> = _editWordState.asStateFlow()

    fun updateEditWordState(editWordState: EditWordState) {
        _editWordState.value = editWordState
    }
}

data class EditWordState(
    val currentSource: Source = Source(),
    val currentWord: Word_B = Word_B(),
    val currentTranslations: List<Translation> = emptyList()
)
