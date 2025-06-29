package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.ui.compose.card.CardWithAnimatedAlpha
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.TitleText

@Composable
fun SessionPillScreenComponent(
    sessions: List<Session>,
    sessionWordsWithTranslations: List<WordWithTranslations>,
    selectedSessionState: Session?,
    readSession: (id: Int) -> Session,
    updateSelectedSession: (session: Session) -> Unit,
    updateSession: () -> Unit,
    restartSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSession by remember { mutableStateOf(selectedSessionState) }
    var selectedSessionId by remember { mutableStateOf(selectedSessionState?.id) }
    var selectedSessionLabel by remember {
        mutableStateOf(
            if (selectedSession == null) "Select Session"
            else if (selectedSession?.name?.isEmpty() == true) "Select session"
            else selectedSession!!.name
        )
    }
    var isSelectSessionError by remember { mutableStateOf(false) }

    Column {
        Column {
            EntityDropdownMenu(
                list = sessions,
                defaultValue = selectedSessionLabel,
                onSelect = {
                    selectedSessionId = it.id
                    selectedSessionLabel = it.name
                    selectedSession = readSession(selectedSessionId!!)
                    updateSelectedSession(selectedSession!!)
                },
                resetErrorStateOnClick = { isSelectSessionError = it },
            )
            HorizontalDivider(thickness = 4.dp, color = Color.Black)
        }

        if (selectedSession != null) {
            Column {
                Column(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth()) {
                        Column(modifier = modifier.weight(0.5f)) {
                            Button(
                                modifier = modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onClick = { updateSession() }
                            ) {
                                Text(
                                    text = "Get more",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Column(modifier = modifier.weight(0.5f)) {
                            Button(
                                modifier = modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                onClick = { restartSession() }
                            ) {
                                Text(
                                    text = "Restart session",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
                HorizontalDivider()
                HorizontalDivider()
                if (sessionWordsWithTranslations.isNotEmpty()) {
                    Column {
                        HorizontalDivider()
                        LazyColumn {
                            items(sessionWordsWithTranslations) { wordWithTranslations ->
                                CardWithAnimatedAlpha(word = wordWithTranslations.word,
                                                      translations = wordWithTranslations.translations
                                )
                            }
                        }
                    }
                }
                if (sessionWordsWithTranslations.isEmpty()) {
                    TitleText(text = "There are no words left.\nPlease, restart session.")
                }
            }
        } else {
            TitleText(text = "Please, select session")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SessionPillScreenComponentEmptyPreview() {
    SessionPillScreenComponent(
        sessions = PreviewData.sessions,
        selectedSessionState = PreviewData.session1,
        readSession = { _ -> PreviewData.session1 },
        updateSelectedSession = { },
        sessionWordsWithTranslations = emptyList(),
        updateSession = { },
        restartSession = { },
    )
}

@Preview(showBackground = true)
@Composable
fun SessionPillScreenComponentNotSelectedPreview() {
    SessionPillScreenComponent(
        sessions = PreviewData.sessions,
        selectedSessionState = null,
        readSession = { _ -> PreviewData.session1 },
        updateSelectedSession = { },
        sessionWordsWithTranslations = emptyList(),
        updateSession = { },
        restartSession = { },
    )
}

@Preview(showBackground = true)
@Composable
fun SessionPillScreenComponentPreview() {
    SessionPillScreenComponent(
        sessions = PreviewData.sessions,
        selectedSessionState = PreviewData.session1,
        readSession = { _ -> PreviewData.session1 },
        updateSelectedSession = { },
        sessionWordsWithTranslations = PreviewData.wordsWithTranslations,
        updateSession = { },
        restartSession = { },
    )
}
