package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.reusable.EntityDropdownMenu
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.reusable.PillData
import com.dkat.wordbook.ui.compose.reusable.PillSwitch
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.viewModel.screen.EditableSessionState

@Composable
fun SessionsScreen(
    navController: NavController,

    isSessionOpen: Boolean,
    isManageSessionOpen: Boolean,
    openSession: () -> Unit,
    openManageSession: () -> Unit,

    sources: List<Source>,
    readSourceById: (sourceId: Int) -> Source,

    sessions: List<Session>,
    selectedSessionState: Session,
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

@Composable
fun SessionPillScreenComponent(
    sessions: List<Session>,
    selectedSessionState: Session,
    readSession: (id: Int) -> Session,
    updateSelectedSession: (session: Session) -> Unit,
    modifier: Modifier
) {
    var selectedSession by remember { mutableStateOf<Session?>(selectedSessionState) }
    var selectedSessionId by remember { mutableStateOf<Int?>(selectedSessionState.id) }
    var selectedSessionLabel by remember {
        mutableStateOf(
            if (selectedSession?.name?.isEmpty() == true) "Select session"
            else selectedSession!!.name
        )
    }
    var isSelectSessionError by remember { mutableStateOf(false) }

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
}

@Composable
fun ManageSessionPillScreenComponent(
    navController: NavController,

    sources: List<Source>,
    readSourceById: (sourceId: Int) -> Source,
    deleteSession: (session: Session) -> Unit,
    updateEditableSessionState: (editableSessionState: EditableSessionState) -> Unit,
    readSourcesBySessionId: (sessionId: Int) -> List<Source>,

    sessions: List<Session>,
    createSession: (source: Source, session: Session) -> Unit,

    modifier: Modifier
) {
    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new session",
            isHideTitleOnExpand = true,
        ) {
            InputSession(
                sessions = sessions,
                sources = sources,
                createSession = createSession,
                readSourceById = readSourceById
            )
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)
        ListOfSessions(
            navController = navController,
            sessions = sessions,
            deleteSession = deleteSession,
            updateEditableSessionState = updateEditableSessionState,
            readSourcesBySessionId = readSourcesBySessionId,
            modifier = modifier
        )
    }
}

@Composable
fun ListOfSessions(navController: NavController,
                   sessions: List<Session>,
                   deleteSession: (session: Session) -> Unit,
                   updateEditableSessionState: (editableSessionState: EditableSessionState) -> Unit,
                   readSourcesBySessionId: (sessionId: Int) -> List<Source>,
                   modifier: Modifier
) {
    Column(modifier = Modifier.padding(8.dp)) {
        sessions.forEach { session ->
            SessionItem(
                navController = navController,
                session = session,
                deleteSession = deleteSession,
                updateEditableSessionState = updateEditableSessionState,
                readSourcesBySessionId = readSourcesBySessionId,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun SessionItem(navController: NavController,
                session: Session,
                deleteSession: (session: Session) -> Unit,
                updateEditableSessionState: (editableSessionState: EditableSessionState) -> Unit,
                readSourcesBySessionId: (sessionId: Int) -> List<Source>,
                modifier: Modifier
) {
    val editableSessionState = EditableSessionState(
        currentSession = session,
        currentSources = readSourcesBySessionId(session.id)
    )
    EditableDeletableItem(navController = navController,
                          editRoute = Screen.EditSession.route,
                          titleValue = session.name,
                          editableObject = editableSessionState,
                          updateEditableObject = updateEditableSessionState,
                          editDescription = "edit session",
                          deletableObject = session,
                          deleteObject = deleteSession,
                          deleteDescription = "delete session",
                          additionalContent = null,
                          modifier = modifier
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
        readSourcesBySessionId = { _ -> PreviewData.sources }
    )
}

@Preview(showBackground = true)
@Composable
fun SessionScreenEmptyPreview() {
    SessionsScreen(
        sources = PreviewData.sources,
        modifier = Modifier,
        sessions = PreviewData.sessions,
        selectedSessionState = PreviewData.session1,
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
        readSourcesBySessionId = { _ -> PreviewData.sources }
    )
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
        readSourcesBySessionId = { _ -> PreviewData.sources }
    )
}
