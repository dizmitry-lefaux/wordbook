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
import com.dkat.wordbook.data.entity.LanguageAndOrder
import com.dkat.wordbook.ui.compose.reusable.ExpandableSection
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

private const val TAG = "LanguagesPillScreen"

@Composable
fun LanguagesPillScreen(
    modifier: Modifier,
    createLanguage: (language: Language) -> Unit,
    navController: NavController,
    deleteLanguage: (language: Language) -> Unit,
    readLanguages: () -> List<LanguageAndOrder>,
    updateLanguageState: (language: Language) -> Unit,
    updateLanguagesOrder: (languages: List<LanguageAndOrder>) -> Unit,
) {
    // workaround for recompose on add / move / remove language actions
    var recomposeTrigger by remember { mutableStateOf(true) }
    var coroutineScope = rememberCoroutineScope()

    Column {
        ExpandableSection(
            modifier = modifier.fillMaxWidth(),
            // TODO: move to string resources
            title = "Add new language",
            isHideTitleOnExpand = true,
        ) {
            InputLanguage(createLanguage = createLanguage,
                          languages = readLanguages().map { it -> it.language },
                          onAddLanguageEvent = {
                              coroutineScope.launch {
                                  recomposeTrigger = !recomposeTrigger
                                  sleep(100L)
                              }
                          })
        }
        HorizontalDivider(thickness = 4.dp, color = Color.Black)

        if (recomposeTrigger) {
            LanguagesList(
                navController = navController,
                languages = readLanguages(),
                deleteLanguage = deleteLanguage,
                onDeleteEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                updateLanguageState = updateLanguageState,
                updateLanguagesOrder = updateLanguagesOrder,
                onMoveEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                modifier = modifier
            )
        }
        if (!recomposeTrigger) {
            LanguagesList(
                navController = navController,
                languages = readLanguages(),
                deleteLanguage = deleteLanguage,
                onDeleteEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                updateLanguageState = updateLanguageState,
                updateLanguagesOrder = updateLanguagesOrder,
                onMoveEvent = {
                    coroutineScope.launch {
                        recomposeTrigger = !recomposeTrigger
                        sleep(100L)
                    }
                },
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguagesPillScreenPreview() {
    LanguagesPillScreen(modifier = Modifier,
                        createLanguage = { },
                        navController = rememberNavController(),
                        deleteLanguage = { },
                        readLanguages = { -> PreviewData.languageAndOrderList },
                        updateLanguageState = { },
                        updateLanguagesOrder = { _ -> })
}
