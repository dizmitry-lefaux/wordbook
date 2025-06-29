package com.dkat.wordbook.ui.compose.screen.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.screen.session.ChooseSourceComponent
import com.dkat.wordbook.viewModel.screen.EditableSessionState

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
    // mutableStateListOf consumes vararg of list elements
    // toTypedArray() -> convert list of elements to array of elements
    // * -> convert array to vararg of elements
    var selectedSources = remember {
        mutableStateListOf(*(
                editableSessionState.currentSources.mapIndexed { index, sourceLocal ->
                    IndexedSource(index, sourceLocal)
                }.toTypedArray())
        )
    }

    var lastAddedSourceIndex by remember { mutableIntStateOf(selectedSources.size) }

    var isSessionError by remember { mutableStateOf(false) }
    var sessionErrorText by remember { mutableStateOf("") }

    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }

    Popup(properties = PopupProperties(focusable = true)) {
        Column(modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CloseablePopupTitle(
                navController = navController,
                // TODO: move to string resources
                titleText = "Edit session",
            )
            LazyColumn {
                item {
                    Column {
                        Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                             // TODO: move to string resources
                             text = "name:"
                        )
                        TextField(
                            value = sessionNameInput,
                            onValueChange = { currentSessionName ->
                                sessionNameInput = currentSessionName
                                isSessionError = false
                                sessionErrorText = ""
                                if (currentSessionName.isEmpty()) {
                                    // TODO: move to string resources
                                    sessionNameInput = "input session name"
                                }
                                if (sessions.map { session -> session.name }
                                        .toList().contains(currentSessionName)
                                    && currentSessionName != session.name
                                ) {
                                    isSessionError = true
                                    sessionErrorText =
                                        "session '$currentSessionName' already exists"
                                }
                            },
                            placeholder = {
                                // TODO: move to string resources
                                Text("input session name")
                            },
                            modifier = modifier.padding(8.dp),
                            isError = isSessionError,
                            supportingText = {
                                if (isSessionError) {
                                    ErrorSupportingText(errorText = sessionErrorText)
                                }
                            },
                        )
                        HorizontalDivider(thickness = 2.dp)
                        Text(modifier = modifier.padding(start = 8.dp, top = 8.dp),
                             // TODO: move to string resources
                             text = "sources:"
                        )
                    }
                }

                items(items = selectedSources,
                      key = { selectedSource -> selectedSource.index }
                ) { selectedSource ->
                    ChooseSourceComponent(
                        currentSource = selectedSource.source,
                        sources = sources,
                        getIdOnRemoveClick = {
                            selectedSources.removeIf { src ->
                                src.source.id == it
                            }
                        },
                        getValueOnChange = { changingSource ->
                            var newSource = sources.find { src ->
                                src.id == changingSource.id
                            }!!
                            selectedSources.replaceAll { replaceableSource ->
                                if (selectedSource.index == replaceableSource.index) {
                                    IndexedSource(replaceableSource.index, newSource)
                                } else replaceableSource
                            }
                        },
                    )
                }
                item {
                    EntityDropdownMenu(list = sources,
                                       defaultValue = "select source",
                                       onSelect = {
                                           var selectedSource = sources.findLast { src ->
                                               src.id == it.id
                                           }
                                           lastAddedSourceIndex += 1
                                           selectedSources.add(IndexedSource(lastAddedSourceIndex,
                                                                             selectedSource!!
                                           )
                                           )
                                       },
                                       resetErrorStateOnClick = { isSourceError = it })
                }

                item {
                    Row {
                        Button(modifier = modifier.padding(8.dp), onClick = {
                            // TODO: extract validations to separate method if possible
                            if (sessions.map { it.name }.toList().contains(sessionNameInput)
                                && sessionNameInput != editableSessionState.currentSession.name
                            ) {
                                isSessionError = true
                                // TODO: move to string resources
                                sessionErrorText = "session name not unique"
                            }
                            if (sessionNameInput.isEmpty()) {
                                isSessionError = true
                                // TODO: move to string resources
                                sessionErrorText = "session name should not be empty"
                            }
                            if (selectedSources.isEmpty()) {
                                isSourceError = true
                                sourceErrorText = "at least one source should be selected"
                            }
                            var uniqueSourceIds =
                                selectedSources.toList().map { source -> source.source.id }
                                    .distinct()
                            if (uniqueSourceIds.size != selectedSources.size) {
                                isSourceError = true
                                sourceErrorText = "sources should be unique across selected"
                            }
                            if (!isSessionError && !isSourceError) {
                                session = Session(name = sessionNameInput)
                                editSession(session,
                                            selectedSources.toList().map { src -> src.source })
                                sessionNameInput = ""
                                selectedSources.clear()
                                isSessionError = false
                                sessionErrorText = ""
                                isSourceError = false
                                sourceErrorText = ""
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
}

private class IndexedSource(
    var index: Int,
    var source: Source
) {
    override fun toString(): String {
        return "IndexedSource(index=$index, source=$source)"
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
