package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText

@Composable
fun InputLanguage(
    languages: List<Language>,
    createLanguage: (language: Language) -> Unit,
    modifier: Modifier = Modifier,
) {
    var nameInput by remember { mutableStateOf("") }
    var language by remember { mutableStateOf(Language()) }
    var isError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    Column {
        TextField(
            value = nameInput,
            onValueChange = {
                nameInput = it
                isError = false
            },
            placeholder = {
                // TODO: move to string resources
                Text("input language name")
            },
            modifier = modifier.padding(8.dp),
            isError = isError,
            supportingText = {
                if (isError) {
                    ErrorSupportingText(errorText = errorText)
                }
            },
        )
        Button(modifier = modifier.padding(8.dp), onClick = {
            if (!languages.map { it.name }.toList().contains(nameInput)) {
                language = Language(name = nameInput)
                nameInput = ""
                createLanguage(language)
                isError = false
            } else {
                // TODO: move to string resources
                errorText = "language $nameInput already exists"
                isError = true
            }
        }) {
            // TODO: move to string resources
            ButtonText(buttonText = "Add language")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputLanguagePreview() {
    InputLanguage(languages = PreviewData.languages, createLanguage = {})
}
