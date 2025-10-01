package com.example.designsystem.theme.colors

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ArticleiaColorScheme(
    val primaryA: Color,
    val primaryB: Color,
    val contentA: Color,
    val contentB: Color,
    val contentC: Color,
    val contentD: Color,
    val tabContainer: Color,
    val tabIndicator: Color,
)

internal val LocalArticleiaAppColors = staticCompositionLocalOf { LightColors }
