package com.dkat.wordbook.ui.compose.screen.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Session
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.viewModel.screen.EditableSessionState

@Composable
fun ManageSessionPillScreenComponent(
    navController: NavController,

    sources: List<Source>,
    deleteSession: (session: Session) -> Unit,
    updateEditableSessionState: (editableSessionState: EditableSessionState) -> Unit,
    readSourcesBySessionId: (sessionId: Int) -> List<Source>,

    sessions: List<Session>,
    createSession: (sources: List<Source>, session: Session) -> Unit,

    modifier: Modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun ManageSessionScreenPillPreview() {
    ManageSessionPillScreenComponent(navController = rememberNavController(),
                                     sources = PreviewData.sources,
                                     deleteSession = { },
                                     updateEditableSessionState = { },
                                     readSourcesBySessionId = { _ -> PreviewData.sources },
                                     sessions = PreviewData.sessions,
                                     createSession = { _, _ -> }
    )
}
