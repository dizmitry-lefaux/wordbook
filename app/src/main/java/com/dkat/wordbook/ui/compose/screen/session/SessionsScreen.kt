package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.WordWithTranslations
import com.dkat.wordbook.ui.compose.reusable.PillData
import com.dkat.wordbook.ui.compose.reusable.PillSwitch
import com.dkat.wordbook.viewModel.screen.EditableSessionState

@Composable
fun SessionsScreen(
    navController: NavController,

    sessionWordsWithTranslations: List<WordWithTranslations>,
    restartSession: () -> Unit,
    updateSession: () -> Unit,

    isSessionOpen: Boolean,
    isManageSessionOpen: Boolean,
    openSession: () -> Unit,
    openManageSession: () -> Unit,

    sources: List<Source>,
    readSourceById: (sourceId: Int) -> Source,

    sessions: List<Session>,
    selectedSessionState: Session?,
    createSession: (source: Source, session: Session) -> Unit,
    readSession: (id: Int) -> Session,
    deleteSession: (session: Session) -> Unit,
    updateSelectedSession: (session: Session) -> Unit,
    updateEditableSessionState: (editableSessionState: EditableSessionState) -> Unit,
    readSourcesBySessionId: (sessionId: Int) -> List<Source>,

    modifier: Modifier = Modifier
) {
    Column {
        Column {
            PillSwitch(
                pills = listOf(
                    PillData(title = "SESSION",
                             isSelected = isSessionOpen,
                             onClick = {
                                 if (!isSessionOpen) {
                                     openSession()
                                 }
                             }
                    ),
                    PillData(title = "MANAGE",
                             isSelected = isManageSessionOpen,
                             onClick = {
                                 if (!isManageSessionOpen) {
                                     openManageSession()
                                 }
                             }
                    )
                )
            )
        }
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            if (isSessionOpen) {
                SessionPillScreenComponent(sessions = sessions,
                                           selectedSessionState = selectedSessionState,
                                           readSession = readSession,
                                           updateSelectedSession = updateSelectedSession,
                                           sessionWordsWithTranslations = sessionWordsWithTranslations,
                                           updateSession = updateSession,
                                           restartSession = restartSession,
                                           modifier = modifier
                )
            }
            if (!isSessionOpen) {
                ManageSessionPillScreenComponent(sources = sources,
                                                 readSourceById = readSourceById,
                                                 sessions = sessions,
                                                 createSession = createSession,
                                                 navController = navController,
                                                 deleteSession = deleteSession,
                                                 updateEditableSessionState = updateEditableSessionState,
                                                 readSourcesBySessionId = readSourcesBySessionId,
                                                 modifier = modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageSessionScreenPreview() {
    SessionsScreen(
        sources = PreviewData.sources,
        modifier = Modifier,
        sessions = PreviewData.sessions,
        selectedSessionState = PreviewData.session1,
        createSession = { _, _ -> },
        readSourceById = { _ -> Source() },
        readSession = { _ -> Session() },
        updateSelectedSession = { _ -> },
        isSessionOpen = false,
        isManageSessionOpen = true,
        openSession = { },
        openManageSession = { },
        navController = rememberNavController(),
        deleteSession = { },
        updateEditableSessionState = { },
        readSourcesBySessionId = { _ -> PreviewData.sources },
        sessionWordsWithTranslations = PreviewData.wordsWithTranslations,
        restartSession = { },
        updateSession = { }
    )
}


@Preview(showBackground = true)
@Composable
fun SessionScreenWithValuesPreview() {
    SessionsScreen(
        sources = PreviewData.sources,
        modifier = Modifier,
        sessions = PreviewData.sessions,
        selectedSessionState = PreviewData.session1,
        createSession = { _, _ -> },
        readSession = { _ -> Session() },
        readSourceById = { _ -> Source() },
        updateSelectedSession = { _ -> },
        isSessionOpen = true,
        isManageSessionOpen = false,
        openSession = { },
        openManageSession = { },
        navController = rememberNavController(),
        deleteSession = { },
        updateEditableSessionState = { },
        readSourcesBySessionId = { _ -> PreviewData.sources },
        sessionWordsWithTranslations = PreviewData.wordsWithTranslations,
        restartSession = { },
        updateSession = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SessionScreenEmptyPreview() {
    SessionsScreen(
        sources = emptyList(),
        modifier = Modifier,
        sessions = emptyList(),
        selectedSessionState = Session(),
        createSession = { _, _ -> },
        readSourceById = { _ -> Source() },
        readSession = { _ -> Session() },
        updateSelectedSession = { _ -> },
        isSessionOpen = true,
        isManageSessionOpen = false,
        openSession = { },
        openManageSession = { },
        navController = rememberNavController(),
        deleteSession = { },
        updateEditableSessionState = { },
        readSourcesBySessionId = { _ -> PreviewData.sources },
        sessionWordsWithTranslations = PreviewData.wordsWithTranslations,
        restartSession = { },
        updateSession = { }
    )
}
