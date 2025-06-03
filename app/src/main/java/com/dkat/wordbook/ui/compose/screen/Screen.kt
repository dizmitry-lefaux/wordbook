package com.dkat.wordbook.ui.compose.screen

import androidx.annotation.StringRes
import com.dkat.wordbook.R

sealed class Screen(
    val route: String,
    val icon: Int?,
    @StringRes
    val labelResourceId: Int
) {
    data object Home : Screen(
        route = "home",
        icon = R.drawable.home3,
        labelResourceId = R.string.home_label
    )

    data object Session : Screen(
        route = "session",
        icon = R.drawable.session,
        labelResourceId = R.string.session_label
    )

    data object Words : Screen(
        route = "words",
        icon = R.drawable.book,
        labelResourceId = R.string.words
    )

    data object Books : Screen(
        route = "books",
        icon = R.drawable.books,
        labelResourceId = R.string.books
    )

    data object EditWord: Screen(
        route = "editWord",
        icon = null,
        labelResourceId = R.string.edit_word
    )

    data object EditLanguage: Screen(
        route = "editLanguage",
        icon = null,
        labelResourceId = R.string.edit_language
    )

    data object EditSource: Screen(
        route = "editSource",
        icon = null,
        labelResourceId = R.string.edit_source
    )

    data object EditSession: Screen(
        route = "editSession",
        icon = null,
        labelResourceId = R.string.edit_session
    )
}

val bottomBarScreens = listOf(Screen.Home, Screen.Session, Screen.Words, Screen.Books)
