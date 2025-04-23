package com.dkat.wordbook.ui.compose.screen.home

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
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word_B
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.reusable.ErrorText

private const val TAG = "InputWord"

@Composable
fun InputWord(
    sources: List<Source>,
    readSourceById: (id: Int) -> Source,
    createWordWithTranslation: (word: Word_B, translation: Translation) -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf("") }
    var translationInput by remember { mutableStateOf("") }
    var word by remember { mutableStateOf(Word_B()) }
    var translation by remember { mutableStateOf(Translation()) }
    var sourceId by remember { mutableStateOf<Int?>(null) }
    var source by remember { mutableStateOf<Source?>(null) }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }
    var isTranslationInputError by remember { mutableStateOf(false) }
    var translationInputErrorText by remember { mutableStateOf("") }
    var isSelectSourceError by remember { mutableStateOf(false) }
    var selectSourceErrorText by remember { mutableStateOf("") }

    Column {
        TextField(
            value = origInput,
            onValueChange = {
                origInput = it
                isOriginalInputError = false
            },
            placeholder = {
                // TODO: move to string resources
                Text("input original")
            },
            modifier = modifier.padding(8.dp),
            isError = isOriginalInputError,
            supportingText = {
                if (isOriginalInputError) {
                    ErrorSupportingText(errorText = originalInputErrorText)
                }
            },
        )
        TextField(
            value = translationInput,
            onValueChange = {
                translationInput = it
                isTranslationInputError = false
            },
            placeholder = {
                // TODO: move to string resources
                Text("input translation")
            },
            modifier = modifier.padding(8.dp),
            isError = isTranslationInputError,
            supportingText = {
                if (isTranslationInputError) {
                    ErrorSupportingText(errorText = translationInputErrorText)
                }
            },
        )
        EntityDropdownMenu(list = sources,
                           defaultValue = "select source",
                           onSelect = {
                               sourceId = it.id
                               source = readSourceById(sourceId!!)
                           },
                           resetErrorStateOnClick = { isSelectSourceError = it }
        )
        if (isSelectSourceError) {
            ErrorText(errorText = selectSourceErrorText)
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = {
                if (sourceId == null) {
                    isSelectSourceError = true
                    // TODO: move to string resources
                    selectSourceErrorText = "source not selected"
                }
                if (origInput.isEmpty()) {
                    isOriginalInputError = true
                    // TODO: move to string resources
                    originalInputErrorText = "original is empty"
                }
                if (translationInput.isEmpty()) {
                    isTranslationInputError = true
                    // TODO: move to string resources
                    translationInputErrorText = "translation is empty"
                }
                if (!isOriginalInputError && !isTranslationInputError && !isSelectSourceError) {
                    word = Word_B(
                        sourceId = source!!.id,
                        languageId = source!!.mainOrigLangId,
                        value = origInput
                    )
                    translation = Translation(
                        value = translationInput,
                        languageId = source!!.mainTranslationLangId
                    )
                    createWordWithTranslation(word, translation)
                    origInput = ""
                    translationInput = ""
                    isOriginalInputError = false
                    isTranslationInputError = false
                    isSelectSourceError = false
                }
            }
        ) {
            // TODO: move to string resources
            ButtonText(buttonText = "Submit values")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputWordPreview()
{
    InputWord(
        sources = listOf(
            Source(
                id = 3368,
                name = "Chuck Leonard",
                mainOrigLangId = 6743,
                mainTranslationLangId = 9292
            ),
            Source(
                id = 5360,
                name = "Wendell Patrick",
                mainOrigLangId = 7709,
                mainTranslationLangId = 6304
            )
        ),
        readSourceById = {_ -> Source()},
        createWordWithTranslation = { _, _ -> }
    )
}
