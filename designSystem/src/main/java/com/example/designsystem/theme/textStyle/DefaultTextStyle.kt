package com.example.designsystem.theme.textStyle

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.designsystem.R

private val goldPlay =
    FontFamily(
        Font(R.font.goldplay_bold, FontWeight.Bold),
        Font(R.font.goldplay_medium, FontWeight.Medium),
    )
internal val defaultTextStyle =
    ArticleiaTextStyle(
        hugeTitle =
            SizedTextStyle(
                bold =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 72.sp,
                        lineHeight = 84.sp,
                    ),
                medium =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 72.sp,
                        lineHeight = 84.sp,
                    ),
            ),
        display =
            SizedTextStyle(
                bold =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        lineHeight = 56.sp,
                    ),
                medium =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 48.sp,
                        lineHeight = 56.sp,
                    ),
            ),
        title =
            SizedTextStyle(
                bold =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        lineHeight = 36.sp,
                    ),
                medium =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 32.sp,
                        lineHeight = 36.sp,
                    ),
            ),
        body =
            SizedTextStyle(
                bold =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                    ),
                medium =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                    ),
            ),
        captionOne =
            SizedTextStyle(
                bold =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                    ),
                medium =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                    ),
            ),
        captionTwo =
            SizedTextStyle(
                bold =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                    ),
                medium =
                    TextStyle(
                        fontFamily = goldPlay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                    ),
            ),
    )

internal val LocalArticleiaTextStyle = staticCompositionLocalOf { defaultTextStyle }
