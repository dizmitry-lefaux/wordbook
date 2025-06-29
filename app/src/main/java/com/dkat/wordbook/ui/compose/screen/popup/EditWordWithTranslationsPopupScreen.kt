package com.dkat.wordbook.ui.compose.screen.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Translation
import com.dkat.wordbook.data.entity.Word
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.CloseablePopupTitle
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.word.EditTranslation
import com.dkat.wordbook.viewModel.screen.EditableWordState

private const val TAG = "EditWordWithTranslationsPopupScreen"

@Composable
fun EditWordWithTranslationsPopupScreen(
    navController: NavController,
    editableWordState: EditableWordState,
    editWordWithTranslations: (word: Word, translations: List<Translation>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var origInput by remember { mutableStateOf(editableWordState.currentWord.value) }
    var word by remember { mutableStateOf(editableWordState.currentWord) }
    var translation by remember { mutableStateOf(Translation()) }
    val translations = remember { mutableStateListOf<Translation>() }
    var lastTranslationInputFieldId by remember {
        mutableIntStateOf(editableWordState.currentTranslations.size)
    }
    var currentTranslationInputValue by remember { mutableStateOf("") }
    // mutableStateListOf consumes vararg of list elements
    // toTypedArray() -> convert list of elements to array of elements
    // * -> convert array to vararg of elements
    var translationInputs = remember {
        mutableStateListOf(*(
                editableWordState.currentTranslations.mapIndexed { index, translationLocal ->
                    IndexedTranslationInput(index, translationLocal.value)
                }.toTypedArray())
        )
    }

    var isOriginalInputError by remember { mutableStateOf(false) }
    var originalInputErrorText by remember { mutableStateOf("") }

    Popup(properties = PopupProperties(focusable = true)) {
        Column(modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CloseablePopupTitle(
                navController = navController,
                // TODO: move to string resources
                titleText = "Edit word",
            )
            LazyColumn {
                item {
                    Column {
                        Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                            // TODO: move to string resources
                             text = "original word:"
                        )
                        TextField(
                            value = origInput,
                            onValueChange = {
                                origInput = it
                                isOriginalInputError = false
                                if (it.isEmpty()) {
                                    // TODO: move to string resources
                                    origInput = "input original word"
                                }
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
                        HorizontalDivider(thickness = 2.dp)
                        // TODO: move to string resources
                        Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                             text = "translations:"
                        )
                    }
                }
                items(items = translationInputs,
                      key = { translationInput -> translationInput.index }
                ) { translationInput ->
                    Row {
                        EditTranslation(
                            id = translationInput.index,
                            value = translationInput.value,
                            getIdOnRemoveClick = {
                                translationInputs.removeIf { it.index == translationInput.index }
                            },
                            getValueOnChange = { currentValue ->
                                currentTranslationInputValue = currentValue
                                translationInputs.replaceAll { replaceableInput ->
                                    if (translationInput.index == replaceableInput.index) {
                                        IndexedTranslationInput(
                                            index = translationInput.index, value = currentValue
                                        )
                                    } else replaceableInput
                                }
                            }
                        )
                    }
                }
                item {
                    IconButton(onClick = {
                        lastTranslationInputFieldId += 1
                        translationInputs.add(IndexedTranslationInput(lastTranslationInputFieldId, ""))
                    }) {
                        Icon(imageVector = Icons.Filled.Add,
                             tint = Color.Black,
                             // TODO: move to string resources
                             contentDescription = "Add translation field"
                        )
                    }
                }
                item {
                    Row {
                        Button(modifier = modifier.padding(8.dp),
                               onClick = {
                                   if (origInput.isEmpty()) {
                                       isOriginalInputError = true
                                       // TODO: move to string resources
                                       originalInputErrorText = "original is empty"
                                   }
                                   translationInputs.forEach { translationInputEntity ->
                                       val localTranslationInput = translationInputEntity.value
                                       word = Word(
                                           id = editableWordState.currentWord.id,
                                           sourceId = editableWordState.currentSource.id,
                                           languageId = editableWordState.currentSource.mainOrigLangId,
                                           value = origInput
                                       )
                                       translation = Translation(
                                           wordId = editableWordState.currentWord.id,
                                           value = localTranslationInput,
                                           languageId = editableWordState.currentSource.mainTranslationLangId
                                       )
                                       translations.add(translation)
                                   }
                                   if (!isOriginalInputError) {
                                       editWordWithTranslations(word, translations.toList())
                                       origInput = ""
                                       translationInputs.clear()
                                       currentTranslationInputValue = ""
                                       lastTranslationInputFieldId += 1
                                       translationInputs.add(IndexedTranslationInput(
                                           index = lastTranslationInputFieldId,
                                           value = currentTranslationInputValue
                                       ))
                                       translations.clear()
                                       navController.popBackStack()
                                   } else {
                                       // TODO
                                   }
                               }
                        ) {
                            // TODO: move to string resources
                            ButtonText(buttonText = "Submit")
                        }
                        Button(
                            modifier = modifier.padding(8.dp),
                            onClick = { navController.popBackStack() }
                        ) {
                            // TODO: move to string resources
                            ButtonText(buttonText = "Cancel")
                        }
                    }
                }
            }
        }
    }
}

private class IndexedTranslationInput(
    var index: Int,
    var value: String
)

@Preview(showBackground = true)
@Composable
fun EditWordWithTranslationsDialogScreenPreview() {
    EditWordWithTranslationsPopupScreen(
        navController = rememberNavController(),
        editableWordState = EditableWordState(
            currentSource = PreviewData.source1,
            currentWord = PreviewData.word1,
            currentTranslations = listOf(PreviewData.translation1, PreviewData.translation2)
        ),
        editWordWithTranslations = { _, _ -> },
    )
}
