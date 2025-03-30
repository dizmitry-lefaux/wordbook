package com.dkat.wordbook.data

data class SourceWithWordsData(
    val sourceName: String,
    val words: List<Word>,
    var isSelected: Boolean = false
)
