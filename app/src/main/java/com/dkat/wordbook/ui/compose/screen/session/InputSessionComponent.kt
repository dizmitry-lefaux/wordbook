package com.dkat.wordbook.ui.compose.screen.session

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
    readSourceById: (sourceId: Int) -> Source,
    createSession: (source: Source, session: Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sessionNameInput by remember { mutableStateOf("") }
    var session by remember { mutableStateOf(Session()) }

    var isSessionError by remember { mutableStateOf(false) }
    var sessionErrorText by remember { mutableStateOf("") }

    var isSourceError by remember { mutableStateOf(false) }
    var sourceErrorText by remember { mutableStateOf("") }
//    var sourceNameInput by remember { mutableStateOf("") }
    var sourceId by remember { mutableStateOf<Int?>(null) }
    var source by remember { mutableStateOf(Source()) }

    Column {
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
                  })
        EntityDropdownMenu(list = sources,
                           defaultValue = "select source",
                           onSelect = { sourceId = it.id },
                           resetErrorStateOnClick = { isSourceError = it })
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
            if (sourceId == null) {
                isSourceError = true
                sourceErrorText = "source should be selected"
            }
            if (!isSessionError && !isSourceError) {
                session = Session(name = sessionNameInput)
                source = readSourceById(sourceId!!)
                createSession(source, session)
                sessionNameInput = ""
                isSessionError = false
                sessionErrorText = ""
            }
        }) {
            // TODO: move to string resources
            ButtonText(buttonText = "Add session")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputSessionPreview() {
    InputSession(
        sources = PreviewData.sources,
        sessions = emptyList(),
        createSession = { _, _ -> },
        readSourceById = { _ -> PreviewData.source1 }
    )
}
