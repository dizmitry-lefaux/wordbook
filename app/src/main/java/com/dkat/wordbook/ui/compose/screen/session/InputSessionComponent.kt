package com.dkat.wordbook.ui.compose.screen.session

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.ButtonText
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ErrorSupportingText
import com.dkat.wordbook.ui.compose.reusable.ErrorText

private const val TAG = "InputSession"

@Composable
fun InputSession(
    sources: List<Source>,
    sessions: List<Session>,
    createSession: (sources: List<Source>, session: Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sessionNameInput by remember { mutableStateOf("") }
    var session by remember { mutableStateOf(Session()) }

    var isSessionError by remember { mutableStateOf(false) }
    var sessionErrorText by remember { mutableStateOf("") }

    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }
    var selectedSources = remember { mutableStateListOf<IndexedSource>() }
    var lastAddedSourceIndex by remember { mutableIntStateOf(0) }

    LazyColumn {
        item {
            TextField(value = sessionNameInput,
                      onValueChange = {
                          sessionNameInput = it
                          isSessionError = false
                      },
                      // TODO: Move text to string resources
                      placeholder = { Text(text = "session name") },
                      modifier = modifier.padding(8.dp),
                      isError = isSessionError,
                      supportingText = {
                          if (isSessionError) {
                              ErrorSupportingText(sessionErrorText)
                          }
                      }
            )
        }
        items(items = selectedSources,
              key = { selectedSource -> selectedSource.index }
        ) { selectedSource ->
            Log.i(TAG,
                  "selectedSources in InputSession: ${selectedSources.toList().joinToString(", ")}"
            )
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
            if (isSourceError) {
                ErrorText(errorText = sourceErrorText)
            }
            Button(modifier = modifier.padding(8.dp), onClick = {
                // TODO: extract validations to separate method if possible
                if (sessions.map { it.name }.toList().contains(sessionNameInput)) {
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
                    selectedSources.toList().map { source -> source.source.id }.distinct()
                if (uniqueSourceIds.size != selectedSources.size) {
                    isSourceError = true
                    sourceErrorText = "sources should be unique across selected"
                }
                if (!isSessionError && !isSourceError) {
                    session = Session(name = sessionNameInput)
                    createSession(selectedSources.toList().map { src -> src.source }, session)
                    sessionNameInput = ""
                    selectedSources.clear()
                    isSessionError = false
                    sessionErrorText = ""
                    isSourceError = false
                    sourceErrorText = ""
                }
            }) {
                // TODO: move to string resources
                ButtonText(buttonText = "Add session")
            }
        }
    }
    Column {
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
fun InputSessionPreview() {
    InputSession(
        sources = PreviewData.sources,
        sessions = emptyList(),
        createSession = { _, _ -> },
    )
}
