package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class PopupViewModel : ViewModel() {
    private val _isPopupOpen = MutableStateFlow(false)
    val isPopupOpen: StateFlow<Boolean> = _isPopupOpen.asStateFlow()

    fun updateIsPopupOpen(isPopupOpen: Boolean) {
        _isPopupOpen.value = isPopupOpen
    }
}
