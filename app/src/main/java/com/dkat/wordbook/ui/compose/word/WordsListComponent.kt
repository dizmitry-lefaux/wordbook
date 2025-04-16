package com.dkat.wordbook.ui.compose.word

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dkat.wordbook.data.entity.Word

@Composable
fun WordsList(
    words: List<Word>,
    onDeleteWordClick: (word: Word) -> Unit,
    modifier: Modifier,
) {
    for (word in words) {
        WordItem(
            word = word,
            onDeleteWordItemClick = onDeleteWordClick,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WordsListPreview(
    words: List<Word> = listOf(
        Word(id = 9431, rusValue = "sociis", engValue = "cetero", sourceName = "Emmanuel Noel"),
        Word(id = 3407, rusValue = "sit", engValue = "suavitate", sourceName = "Bryce Maldonado"),
        Word(id = 8897, rusValue = "vim", engValue = "parturient", sourceName = "Antoine Jacobs")
    )
) {
    WordsList(
        words = words,
        onDeleteWordClick = {},
        modifier = Modifier
    )
}
