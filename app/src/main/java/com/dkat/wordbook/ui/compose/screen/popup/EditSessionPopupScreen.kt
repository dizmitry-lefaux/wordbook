package com.dkat.wordbook.ui.compose.screen.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.CloseablePopupTitle
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.screen.session.ChooseSourceComponent
import com.dkat.wordbook.viewModel.screen.EditableSessionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "EditSessionPopupScreen"

@Composable
fun EditSessionPopupScreen(
    navController: NavController,
    editableSessionState: EditableSessionState,
    sessions: List<Session>,
    sources: List<Source>,
    editSession: (session: Session, sources: List<Source>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sessionNameInput by remember { mutableStateOf(editableSessionState.currentSession.name) }
    var session by remember { mutableStateOf(editableSessionState.currentSession) }
    var source by remember { mutableStateOf(Source()) }
    val sessionSources = remember { mutableStateListOf<Source>() }
    // mutableStateMapOf consumes vararg of pairs
    // toTypedArray() -> convert list of Pairs to array of Pairs
    // * -> convert array to vararg of Pairs
    val sourceInputs = remember {
        mutableStateMapOf(*(
                editableSessionState.currentSources.map { sourceLocal ->
                    Pair(sourceLocal.id, sourceLocal.name)
                }.toTypedArray())
        )
    }

    var lastInputFieldId by remember { mutableIntStateOf(editableSessionState.currentSources.size) }

    var isSessionInputError by remember { mutableStateOf(false) }
    var sessionInputErrorText by remember { mutableStateOf("") }

    // isExpanded: workaround to recompose input fields after removal
    var isExpanded by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // needed to have one translation input even if there were attempts to remove it
    if (sourceInputs.isEmpty()) {
        sourceInputs[lastInputFieldId] = ""
    }

    Popup(properties = PopupProperties(focusable = true)) {
        Column(modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CloseablePopupTitle(navController = navController,
                                titleText = "Edit session",
            )
            Column {
                Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                    // TODO: move to string resources
                     text = "name:"
                )
                TextField(
                    value = sessionNameInput,
                    onValueChange = {
                        sessionNameInput = it
                        isSessionInputError = false
                        if (it.isEmpty()) {
                            // TODO: move to string resources
                            sessionNameInput = "input session name"
                        }
                    },
                    placeholder = {
                        // TODO: move to string resources
                        Text("input session name")
                    },
                    modifier = modifier.padding(8.dp),
                    isError = isSessionInputError,
                    supportingText = {
                        if (isSessionInputError) {
                            ErrorSupportingText(errorText = sessionInputErrorText)
                        }
                    },
                )
            }

            Column {
                HorizontalDivider(thickness = 2.dp)
                // TODO: move to string resources
                Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                     text = "sources:"
                )
                if (isExpanded) {
                    sourceInputs.entries.forEach { entry ->
                        Row {
                            ChooseSourceComponent(
                                getIdOnRemoveClick = {
                                    coroutineScope.launch {
                                        isExpanded = false
                                        sourceInputs.remove(entry.key)
                                        delay(100L)
                                        isExpanded = true
                                    }
                                },
                                getValueOnChange = {
                                    sourceInputs.replace(entry.key, it)
                                },
                                sources = sources,
                            )
                        }
                    }
                    IconButton(onClick = {
                        lastInputFieldId += 1
                        sourceInputs[lastInputFieldId] = ""
                    }) {
                        Icon(imageVector = Icons.Filled.Add,
                             tint = Color.Black,
                            // TODO: move to string resources
                             contentDescription = "Add source field"
                        )
                    }
                }
                if (!isExpanded) {
                    sourceInputs.entries.forEach { entry ->
                        Row {
                            ChooseSourceComponent(sources = sources,
                                                  getIdOnRemoveClick = { },
                                                  getValueOnChange = { },
                                                  modifier = modifier
                            )
                        }
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Add,
                             tint = Color.Black,
                            // TODO: move to string resources
                             contentDescription = "Add source field"
                        )
                    }
                }
                // TODO: extract popup buttons
                Row {
                    Button(modifier = modifier.padding(8.dp),
                           onClick = {
                               if (sessionNameInput.isEmpty()) {
                                   isSessionInputError = true
                                   // TODO: move to string resources
                                   sessionInputErrorText = "session name is empty"
                               }
                               sourceInputs.forEach { sourceInputEntity ->
                                   session = Session(
                                       id = editableSessionState.currentSession.id,
                                       name = sessionNameInput
                                   )
                                   source = Source(
                                       id = sourceInputEntity.key
                                   )
                                   sessionSources.add(source)
                               }
                               if (!isSessionInputError) {
                                   editSession(session, sessionSources.toList())
                                   sessionNameInput = ""
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

@Preview(showBackground = true)
@Composable
fun EditSessionScreenPreview() {
    EditSessionPopupScreen(
        navController = rememberNavController(),
        editableSessionState = EditableSessionState(
            currentSession = PreviewData.session1,
            currentSources = PreviewData.sources,
        ),
        editSession = { _, _ -> },
        sessions = PreviewData.sessions,
        sources = PreviewData.sources,
    )
}
