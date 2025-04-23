package com.dkat.wordbook.ui.compose.word

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B

@Composable
fun WordsWithTranslationsList(
    wordsWithTranslations: List<WordWithTranslations>?,
    onDeleteWordClick: (word: Word_B) -> Unit,
    modifier: Modifier,
) {
    if (wordsWithTranslations != null) {
        for (wordWithTranslations in wordsWithTranslations) {
            WordWithTranslationsItem(
                wordWithTranslations = wordWithTranslations,
                onDeleteWordItemClick = onDeleteWordClick,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordsWithTranslationsListPreview(
) {
    WordsWithTranslationsList(
        wordsWithTranslations = listOf(
            WordWithTranslations(
                word = Word_B(id = 4428, sourceId = 4887, languageId = 9685, value = "dictas"),
                translations = listOf(
                    Translation(id = 5050, wordId = 3924, value = "detraxit", languageId = 2641),
                    Translation(id = 3907, wordId = 7219, value = "hendrerit", languageId = 5243)
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                translations = listOf(
                    Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081),
                    Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243),
                    Translation(id = 6495, wordId = 7035, value = "consequat", languageId = 9676)
                )
            ),
            WordWithTranslations(
                word = Word_B(id = 6426, sourceId = 9615, languageId = 7799, value = "nullam"),
                translations = listOf(
                    Translation(id = 1393, wordId = 2937, value = "consequat", languageId = 4081),
                    Translation(id = 6075, wordId = 2539, value = "tellus", languageId = 3243)
                )
            )
        ),
        onDeleteWordClick = {},
        modifier = Modifier
    )
}
