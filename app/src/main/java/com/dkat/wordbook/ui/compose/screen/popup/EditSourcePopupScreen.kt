package com.dkat.wordbook.ui.compose.screen.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.CloseablePopupTitle
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.reusable.ErrorText

@Composable
fun EditSourcePopupScreen(navController: NavController,
                          editableSourceState: Source,
                          sources: List<Source>,
                          languages: List<Language>,
                          editSource: (source: Source) -> Unit,
                          modifier: Modifier = Modifier
) {
    val defaultSourceName = editableSourceState.name
    var nameInput by remember { mutableStateOf(editableSourceState.name) }
    val origLangInput by remember {
        mutableStateOf(languages.findLast { it.id == editableSourceState.mainOrigLangId }?.name)
    }
    val translationLangInput by remember {
        mutableStateOf(languages.findLast { it.id == editableSourceState.mainTranslationLangId }?.name)
    }
    var origLangId by remember { mutableIntStateOf(editableSourceState.mainOrigLangId) }
    var translationLangId by remember { mutableIntStateOf(editableSourceState.mainTranslationLangId) }
    var source by remember { mutableStateOf(Source()) }

    var isOrigLangError by remember { mutableStateOf(false) }
    var origLangErrorText by remember { mutableStateOf("") }
    var isTranslationLangError by remember { mutableStateOf(false) }
    var translationLangErrorText by remember { mutableStateOf("") }
    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }

    Popup(properties = PopupProperties(focusable = true)) {
        Column(modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CloseablePopupTitle(
                navController = navController,
                titleText = "Edit source",
            )
            Column {
                // TODO: move default value to string resources
                EntityDropdownMenu(list = languages,
                                   defaultValue = origLangInput!!,
                                   onSelect = { origLangId = it.id },
                                   resetErrorStateOnClick = { isOrigLangError = it })
                if (isOrigLangError) {
                    ErrorText(errorText = origLangErrorText)
                }
                // TODO: move default value to string resources
                EntityDropdownMenu(list = languages,
                                   defaultValue = translationLangInput!!,
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
                          }
                )
                Row {
                    Button(modifier = modifier.padding(8.dp), onClick = {
                        // TODO: extract validations to separate method if possible
                        if (translationLangId == origLangId) {
                            isTranslationLangError = true
                            // TODO: move to string resources
                            translationLangErrorText =
                                "translation language is the same as the original language"
                        }
                        if (sources.map { it.name }.toList()
                                .contains(nameInput)
                            && nameInput != defaultSourceName
                        ) {
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
                            source = Source(id = editableSourceState.id,
                                            name = nameInput,
                                            mainOrigLangId = origLangId,
                                            mainTranslationLangId = translationLangId
                            )
                            editSource(source)
                            navController.popBackStack()
                        }
                    }) {
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

@Preview(showBackground = true)
@Composable
fun PreviewEditSourcePopupScreen() {
    EditSourcePopupScreen(navController = rememberNavController(),
                          editableSourceState = PreviewData.source1,
                          sources = PreviewData.sources,
                          languages = PreviewData.languages,
                          editSource = {},
                          modifier = Modifier
    )
}
