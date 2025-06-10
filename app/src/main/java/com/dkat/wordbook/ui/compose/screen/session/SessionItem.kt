package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.screen.Screen
import com.dkat.wordbook.viewModel.screen.EditableSessionState

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
