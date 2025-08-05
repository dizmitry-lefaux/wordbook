package com.dkat.wordbook.ui.compose.screen.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.PreviewData
import com.dkat.wordbook.data.entity.Language
import com.dkat.wordbook.data.entity.Source
import com.dkat.wordbook.data.entity.SourceAndOrder
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import com.dkat.wordbook.ui.compose.source.InputSource
import com.dkat.wordbook.ui.compose.source.SourcesList
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

@Composable
fun SourcesPillScreen(modifier: Modifier,
                      createSource: (source: Source) -> Unit,
                      languages: List<Language>,
                      navController: NavController,
                      deleteSource: (source: Source) -> Unit,
                      readSources: () -> List<SourceAndOrder>,
                      updateSourceState: (source: Source) -> Unit,
                      updateSourcesOrder: (sources: List<SourceAndOrder>) -> Unit
) {
    // workaround for recompose on add / move / remove language actions
    var recomposeTrigger by remember { mutableStateOf(true) }
    var coroutineScope = rememberCoroutineScope()

    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new source",
            isHideTitleOnExpand = true,
        ) {
            InputSource(
                createSource = createSource,
                onCreateSourceEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                languages = languages,
                sources = readSources().map { it.source }
            )
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)

        if (recomposeTrigger) {
            SourcesList(
                navController = navController,
                onDeleteSourceItemClick = deleteSource,
                onDeleteEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                onMoveEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                modifier = modifier,
                sources = readSources(),
                languages = languages,
                updateSourceState = updateSourceState,
                updateSourcesOrder = updateSourcesOrder
            )
        }
        if (!recomposeTrigger) {
            SourcesList(
                navController = navController,
                onDeleteSourceItemClick = deleteSource,
                onDeleteEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                onMoveEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                modifier = modifier,
                sources = readSources(),
                languages = languages,
                updateSourceState = updateSourceState,
                updateSourcesOrder = updateSourcesOrder
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SourcesPillScreenComponentPreview() {
    SourcesPillScreen(modifier = Modifier,
                      createSource = { },
                      languages = PreviewData.languages,
                      navController = rememberNavController(),
                      deleteSource = { },
                      readSources = { -> PreviewData.sourceAndOrderList },
                      updateSourceState = { },
                      updateSourcesOrder = { _ -> }
    )
}
