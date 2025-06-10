package com.dkat.wordbook.ui.compose.word

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
 import com.dkat.wordbook.viewModel.screen.EditableWordState
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.data.entity.Word

@Composable
fun WordsWithTranslationsList(
    navController: NavController,
    wordsWithTranslations: List<WordWithTranslations>?,
    onDeleteWordClick: ((word: Word) -> Unit)?,
    readSourceById: (sourceId: Int) -> Source,
    updateEditableWord: (editableWordState: EditableWordState) -> Unit,
    modifier: Modifier,
) {
    if (wordsWithTranslations != null) {
        for (wordWithTranslations in wordsWithTranslations) {
            WordWithTranslationsItem(navController = navController,
                                     wordWithTranslations = wordWithTranslations,
                                     deleteWord = onDeleteWordClick,
                                     readSourceById = readSourceById,
                                     updateEditableWordState = updateEditableWord,
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
        navController = rememberNavController(),
        wordsWithTranslations = PreviewData.wordsWithTranslations,
        updateEditableWord = { _ -> },
        onDeleteWordClick = {},
        readSourceById = { _ -> Source() },
        modifier = Modifier
    )
}
