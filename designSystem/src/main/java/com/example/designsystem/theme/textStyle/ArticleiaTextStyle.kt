package com.example.designsystem.theme.textStyle

import androidx.compose.ui.text.TextStyle

data class ArticleiaTextStyle(
    val hugeTitle: SizedTextStyle,
    val display: SizedTextStyle,
    val title: SizedTextStyle,
    val body: SizedTextStyle,
    val captionOne: SizedTextStyle,
    val captionTwo: SizedTextStyle,
)

data class SizedTextStyle(
    val bold: TextStyle,
    val medium: TextStyle,
)
