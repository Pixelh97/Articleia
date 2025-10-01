package com.example.ui.screens.postDetails.components

import android.R.attr.bottom
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme
import com.example.viewmodel.postDetails.PostDetailsUiState

@Composable
fun CommentCard(
    comment: PostDetailsUiState.PostDetailsCommentUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppTheme.color.contentD)
                .padding(top = 16.dp, bottom = 32.dp)
                .padding(horizontal = 16.dp),
    ) {
        Text(
            text = comment.name,
            style = AppTheme.textStyle.captionOne.bold,
            color = AppTheme.color.primaryA,
        )
        Text(
            text = comment.email,
            style = AppTheme.textStyle.captionTwo.medium,
            color = AppTheme.color.contentB,
        )
        Text(
            text = comment.body,
            style = AppTheme.textStyle.captionOne.medium,
            color = AppTheme.color.contentA,
            modifier = Modifier.padding(top = 10.dp),
        )
    }
}

@Preview(name = "CommentCard")
@Composable
private fun PreviewCommentCard() {
    CommentCard(
        comment =
            PostDetailsUiState.PostDetailsCommentUiState(
                id = 1,
                name = "name",
                email = "email",
                body = "body",
            ),
        modifier = Modifier.padding(16.dp),
    )
}
