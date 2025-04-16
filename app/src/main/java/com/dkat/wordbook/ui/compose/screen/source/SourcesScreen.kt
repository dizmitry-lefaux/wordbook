package com.dkat.wordbook.ui.compose.screen.source

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.source.ListOfSources

@Composable
fun SourcesScreen(
    sources: List<Source>,
    words: List<Word>,
    languages: List<Language>,
    createSource: (source: Source) -> Unit,
    onDeleteWordItemClick: (word: Word) -> Unit,
    onDeleteSourceItemClick: (source: Source) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column {
        InputSource(createSource = createSource, languages = languages, sources = sources)
        Text(
            modifier = modifier.padding(16.dp),
            text = "Sources:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left
        )
        ListOfSources(
            sources = sources,
            words = words,
            onDeleteWordItemClick = onDeleteWordItemClick,
            onDeleteSourceItemClick = onDeleteSourceItemClick,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SourceScreenPreview()
{
    val languages = listOf(
        Language(id = 4426, name = "English"),
        Language(id = 4427, name = "Russian"),
    )
    val sources = listOf(Source(
        id = 3891,
        name = "Jocelyn McDonald",
        mainOrigLangId = 7944,
        mainTranslationLangId = 1725
    ), 
        Source(
            id = 9555,
            name = "Tania Whitney",
            mainOrigLangId = 8479,
            mainTranslationLangId = 4067
        ))
    val words = listOf(
        Word(rusValue = "asdf", engValue = "qwer"),
        Word(rusValue = "asdf2", engValue = "qwer2")
    )
    SourcesScreen(
        sources = sources,
        languages = languages,
        createSource = {},
        onDeleteWordItemClick = {},
        onDeleteSourceItemClick = {},
        words = words,
        modifier = Modifier
    )
}
