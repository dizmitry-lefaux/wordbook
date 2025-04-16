package com.dkat.wordbook.ui.compose.screen.langauge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.entity.Language

@Composable
fun InputLanguage(
    languages: List<Language>,
    createLanguage:(language: Language) -> Unit,
    modifier: Modifier = Modifier,
) {
    var nameInput by remember { mutableStateOf("") }
    var language by remember { mutableStateOf(Language()) }

    Column {
        TextField(
            value = nameInput,
            onValueChange = {
                nameInput = it
            },
            placeholder = {
                Text("language name")
            },
            modifier = modifier.padding(8.dp)
        )
        Button(
            modifier = modifier.padding(8.dp),
            onClick = {
                if (!languages.map { it.name }.toList().contains(nameInput)) {
                    language = Language(name = nameInput,)
                    nameInput = ""
                    createLanguage(language)
                } else {
                    // dkat: TODO: show error
                }
            }
        ) {
            Text(
                text = "Add language",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputLanguagePreview() {
    InputLanguage(
        languages = listOf(
            Language(id = 9419, name = "English")
        ),
        createLanguage = {}
    )
}
