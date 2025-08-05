package com.dkat.wordbook.ui.compose.source

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.ui.compose.reusable.EditableDeletableItem
import com.dkat.wordbook.ui.compose.screen.Screen

@Composable
fun SourceItem(
    onDeleteSourceClick: (source: Source) -> Unit,
    navController: NavController,
    onDeleteEvent: () -> Unit,
    source: Source,
    origLanguage: Language,
    translationLanguage: Language,
    updateSourceState: (source: Source) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier,
         shape = RoundedCornerShape(20.dp)
    ) {
        EditableDeletableItem(navController = navController,
                              editRoute = Screen.EditSource.route,
                              titleValue = source.name,
                              editableObject = source,
                              updateEditableObject = updateSourceState,
                              editDescription = "edit source",
                              onDeleteEvent = onDeleteEvent,
                              deletableObject = source,
                              deleteObject = onDeleteSourceClick,
                              deleteDescription = "delete source",
                              additionalContent = null,
                              modifier = modifier
        )
        Column(modifier.padding(bottom = 4.dp)) {
            Text(
                text = origLanguage.name,
                modifier = modifier.padding(start = 8.dp)
            )
            Text(
                text = translationLanguage.name,
                modifier = modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SourceItemPreview() {
    SourceItem(
        navController = rememberNavController(),
        modifier = Modifier,
        source = PreviewData.source1,
        origLanguage = PreviewData.language1,
        translationLanguage = PreviewData.language2,
        onDeleteSourceClick = {},
        onDeleteEvent = {},
        updateSourceState = { _ -> },
    )
}
