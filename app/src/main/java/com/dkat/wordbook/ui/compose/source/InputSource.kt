package com.dkat.wordbook.ui.compose.source

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
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.reusable.ErrorText

private const val TAG = "InputSource"

@Composable
fun InputSource(
    sources: List<Source>,
    languages: List<Language>,
    createSource: (source: Source) -> Unit,
    onCreateSourceEvent: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var nameInput by remember { mutableStateOf("") }
    var origLangInput by remember { mutableStateOf("") }
    var translationLangInput by remember { mutableStateOf("") }
    var origLangId by remember { mutableStateOf<Int?>(null) }
    var translationLangId by remember { mutableStateOf<Int?>(null) }
    var source by remember { mutableStateOf(Source()) }

    var isOrigLangError by remember { mutableStateOf(false) }
    var origLangErrorText by remember { mutableStateOf("") }
    var isTranslationLangError by remember { mutableStateOf(false) }
    var translationLangErrorText by remember { mutableStateOf("") }
    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }

    Column {
        // TODO: move default value to string resources
        EntityDropdownMenu(list = languages,
                           defaultValue = "original language",
                           onSelect = { origLangId = it.id },
                           resetErrorStateOnClick = { isOrigLangError = it })
        if (isOrigLangError) {
            ErrorText(errorText = origLangErrorText)
        }
        // TODO: move default value to string resources
        EntityDropdownMenu(list = languages,
                           defaultValue = "translation language",
                           onSelect = { translationLangId = it.id },
                           resetErrorStateOnClick = { isTranslationLangError = it })
        if (isTranslationLangError) {
            ErrorText(errorText = translationLangErrorText)
        }
        TextField(value = nameInput,
                  onValueChange = {
                      nameInput = it
                      isSourceError = false
                  },
                  // TODO: Move text to string resources
                  placeholder = { Text(text = "source name") },
                  modifier = modifier.padding(8.dp),
                  isError = isSourceError,
                  supportingText = {
                      if (isSourceError) {
                          ErrorSupportingText(sourceErrorText)
                      }
                  })
        Button(modifier = modifier.padding(8.dp), onClick = {
            // TODO: extract validations to separate method if possible
            if (origLangId == null) {
                isOrigLangError = true
                // TODO: move to string resources
                origLangErrorText = "original language not selected"
            }
            if (translationLangId == null) {
                isTranslationLangError = true
                // TODO: move to string resources
                translationLangErrorText = "translation language not selected"
            }
            if (translationLangId == origLangId) {
                isTranslationLangError = true
                // TODO: move to string resources
                translationLangErrorText =
                    "translation language is the same as the original language"
            }
            if (sources.map { it.name }.toList().contains(nameInput)) {
                isSourceError = true
                // TODO: move to string resources
                sourceErrorText = "source name not unique"
            }
            if (nameInput.isEmpty()) {
                isSourceError = true
                // TODO: move to string resources
                sourceErrorText = "source name should not be empty"
            }
            if (!isSourceError && !isOrigLangError && !isTranslationLangError) {
                source = Source(name = nameInput,
                                mainOrigLangId = origLangId!!,
                                mainTranslationLangId = translationLangId!!
                )
                createSource(source)
                onCreateSourceEvent()
                origLangInput = ""
                translationLangInput = ""
                nameInput = ""
                isSourceError = false
                isOrigLangError = false
                isTranslationLangError = false
            }
        }) {
            // TODO: move to string resources
            ButtonText(buttonText = "Add source")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputSourcePreview() {
    InputSource(
        sources = PreviewData.sources,
        languages = PreviewData.languages,
        createSource = {},
        onCreateSourceEvent = {}
    )
}
