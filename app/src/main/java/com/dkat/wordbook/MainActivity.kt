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
import com.dkat.wordbook.data.WordRepository
import com.dkat.wordbook.ui.compose.WordbookNavHost
import com.dkat.wordbook.ui.compose.bar.BottomBar
import com.dkat.wordbook.ui.compose.bar.TopAppBar
import com.dkat.wordbook.ui.theme.AppTheme

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

        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(WordRepository(context = context)))

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
                            viewModel = viewModel,
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
