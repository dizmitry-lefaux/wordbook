package com.dkat.wordbook.ui.compose.source

import com.dkat.wordbook.data.Word

data class SourceWithWordsData(
    val sourceName: String,
    val words: List<Word>,
    var isSelected: Boolean = false
)
