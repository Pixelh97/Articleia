package com.example.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.designsystem.theme.colors.DarkColors
import com.example.designsystem.theme.colors.LightColors
import com.example.designsystem.theme.colors.LocalArticleiaAppColors

@Composable
fun ArticleiaTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val theme = if (isDarkTheme) DarkColors else LightColors

    CompositionLocalProvider(
        LocalArticleiaAppColors provides theme,
    ) {
        content()
    }
}
