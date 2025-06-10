package com.dkat.wordbook.viewModel.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionsScreenViewModel : ViewModel() {
    private val _isSessionOpen = MutableStateFlow(true)
    val isSessionOpen: StateFlow<Boolean> = _isSessionOpen.asStateFlow()

    private val _isManageSessionOpen = MutableStateFlow(false)
    val isManageSessionOpen: StateFlow<Boolean> = _isManageSessionOpen.asStateFlow()

    fun openSession() {
        _isSessionOpen.value = true
        _isManageSessionOpen.value = false
    }

    fun openManageSession() {
        _isSessionOpen.value = false
        _isManageSessionOpen.value = true
    }
}
