package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.viewModel.screen.EditableSessionState

@Composable
fun ListOfSessions(navController: NavController,
                   sessions: List<Session>,
                   deleteSession: (session: Session) -> Unit,
                   updateEditableSessionState: (editableSessionState: EditableSessionState) -> Unit,
                   readSourcesBySessionId: (sessionId: Int) -> List<Source>,
                   modifier: Modifier
) {
    LazyColumn {
        items(sessions) {session ->
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
