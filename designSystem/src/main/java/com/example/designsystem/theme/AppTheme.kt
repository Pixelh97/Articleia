package com.example.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.designsystem.theme.colors.ArticleiaColorScheme
import com.example.designsystem.theme.colors.LocalArticleiaAppColors
import com.example.designsystem.theme.textStyle.ArticleiaTextStyle
import com.example.designsystem.theme.textStyle.LocalArticleiaTextStyle

object AppTheme {
    val color: ArticleiaColorScheme
        @Composable @ReadOnlyComposable
        get() = LocalArticleiaAppColors.current

    val textStyle: ArticleiaTextStyle
        @Composable @ReadOnlyComposable
        get() = LocalArticleiaTextStyle.current
}
