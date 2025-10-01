package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.AppTheme

@Composable
fun LoadingPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = AppTheme.color.primaryA,
            modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
        )
    }
}

@Preview(name = "LoadingPlaceholder")
@Composable
private fun PreviewLoadingPlaceholder() {
    Box(Modifier.background(Color.White)){ LoadingPlaceholder() }
}
