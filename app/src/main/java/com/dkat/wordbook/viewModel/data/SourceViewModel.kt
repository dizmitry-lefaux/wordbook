package com.dkat.wordbook.viewModel.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dkat.wordbook.data.repo.SourceRepository
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.data.entity.SourceWithWords
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "SourceViewModel"

class SourceViewModel(
    private val sourceRepository: SourceRepository
) : ViewModel() {

    val sources: StateFlow<List<Source>> = sourceRepository.readSources().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val sourcesWithWords: StateFlow<List<SourceWithWords>> = sourceRepository.readSourcesWithWords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun createSource(source: Source): Long {
        var sourceId: Long = 0
        viewModelScope.launch {
            sourceId = sourceRepository.createSource(source)
        }
        return sourceId
    }

    // blocking read, possibly update to non-blocking
    fun readSource(id: Int): Source {
        return sourceRepository.readSource(id)
    }

    fun readSources() : List<SourceAndOrder> {
        return sourceRepository.readSourcesBlocking();
    }

    fun updateSource(source: Source) {
        viewModelScope.launch {
            launch { updateSourcePrivate(source) }.join()
        }
        Log.i(TAG, "Source updated: $source")
    }

    fun updateSourcesOrder(sourcesWithOrder: List<SourceAndOrder>) {
        viewModelScope.launch {
            launch {
                sourceRepository.updateSourcesOrder(sourcesWithOrder)
            }.join()
        }
    }

    private suspend fun updateSourcePrivate(source: Source) {
        viewModelScope.launch {
            sourceRepository.updateSource(source)
        }
    }

    fun deleteSource(source: Source) {
        viewModelScope.launch {
            sourceRepository.deleteSource(source)
        }
    }
}

class SourceViewModelFactory(
    private val sourceRepository: SourceRepository
) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SourceViewModel::class.java)) {
            return SourceViewModel(sourceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
