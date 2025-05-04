package com.dkat.wordbook.ui.compose.word

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word_B

@Composable
fun WordsWithTranslationsList(
    wordsWithTranslations: List<WordWithTranslations>?,
    onDeleteWordClick: ((word: Word_B) -> Unit)?,
    readSourceById: (sourceId: Int) -> Source,
    updateWordWithTranslations: (word: Word_B, translations: List<Translation>) -> Unit,
    modifier: Modifier,
) {
    if (wordsWithTranslations != null) {
        for (wordWithTranslations in wordsWithTranslations) {
            WordWithTranslationsItem(wordWithTranslations = wordWithTranslations,
                                     onDeleteWordItemClick = onDeleteWordClick,
                                     readSourceById = readSourceById,
                                     editWordWithTranslations = updateWordWithTranslations,
                                     modifier = modifier,
            )
            HorizontalDivider(thickness = 2.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordsWithTranslationsListPreview(
) {
    WordsWithTranslationsList(
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        onDeleteWordClick = {},
        readSourceById = { _ -> Source() },
        updateWordWithTranslations = { _, _ -> },
        modifier = Modifier
    )
}
