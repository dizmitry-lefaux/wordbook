package com.dkat.wordbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices.PIXEL_5
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dkat.wordbook.data.repo.LanguageRepository
import com.dkat.wordbook.data.repo.SessionRepository
import com.dkat.wordbook.data.repo.SourceRepository
import com.dkat.wordbook.data.repo.TranslationRepository
import com.dkat.wordbook.data.repo.WordRepository
import com.dkat.wordbook.ui.compose.WordbookNavHost
import com.dkat.wordbook.ui.compose.bar.BottomBar
import com.dkat.wordbook.ui.compose.bar.TopAppBar
import com.dkat.wordbook.ui.theme.AppTheme
import com.dkat.wordbook.viewModel.data.LanguageViewModel
import com.dkat.wordbook.viewModel.data.LanguageViewModelFactory
import com.dkat.wordbook.viewModel.data.SessionViewModel
import com.dkat.wordbook.viewModel.data.SessionViewModelFactory
import com.dkat.wordbook.viewModel.data.SourceViewModel
import com.dkat.wordbook.viewModel.data.SourceViewModelFactory
import com.dkat.wordbook.viewModel.data.WordViewModel
import com.dkat.wordbook.viewModel.data.WordViewModelFactory
import com.dkat.wordbook.viewModel.screen.BooksScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditLanguagePopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditSessionPopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditSourcePopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.EditWordPopupScreenViewModel
import com.dkat.wordbook.viewModel.screen.SessionsScreenViewModel
import com.dkat.wordbook.viewModel.screen.WordsScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordbookApp()
        }
    }
}

@Composable
fun WordbookApp() {
    AppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val context = LocalContext.current

        val sessionViewModel: SessionViewModel =
            viewModel(factory = SessionViewModelFactory(
                wordRepository = WordRepository(context = context),
                sessionRepository = SessionRepository(context = context)
            ))
        val wordViewModel: WordViewModel = viewModel(factory = WordViewModelFactory(
            TranslationRepository(context = context),
            WordRepository(context = context)
        ))
        val languageViewModel: LanguageViewModel =
            viewModel(factory = LanguageViewModelFactory(LanguageRepository(context = context)))
        val sourceViewModel: SourceViewModel =
            viewModel(factory = SourceViewModelFactory(SourceRepository(context = context)))
        val editWordPopupScreenViewModel: EditWordPopupScreenViewModel = viewModel()
        val editLanguageViewModel: EditLanguagePopupScreenViewModel = viewModel()
        val editSourceViewModel: EditSourcePopupScreenViewModel = viewModel()
        val booksScreenViewModel: BooksScreenViewModel = viewModel()
        val wordsScreenViewModel: WordsScreenViewModel = viewModel()
        val sessionsScreenViewModel: SessionsScreenViewModel = viewModel()
        val editSessionViewModel: EditSessionPopupScreenViewModel = viewModel()

        Scaffold(modifier = Modifier.fillMaxSize(),
                 topBar = {
                     TopAppBar(appName = R.string.app_name)
                 },
                 bottomBar = {
                     BottomBar(
                         navController = navController,
                         currentDestination = currentDestination,
                     )
                 }
        ) { innerPadding ->
            WordbookNavHost(navController = navController,
                            wordViewModel = wordViewModel,
                            languageViewModel = languageViewModel,
                            sourceViewModel = sourceViewModel,
                            sessionViewModel = sessionViewModel,
                            editWordPopupScreenViewModel = editWordPopupScreenViewModel,
                            editLanguageViewModel = editLanguageViewModel,
                            editSourceViewModel = editSourceViewModel,
                            booksScreenViewModel = booksScreenViewModel,
                            wordsScreenViewModel = wordsScreenViewModel,
                            editSessionViewModel = editSessionViewModel,
                            sessionsScreenViewModel = sessionsScreenViewModel,
                            modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_5)
@Composable
fun MainActivityPreview() {
    WordbookApp()
}
