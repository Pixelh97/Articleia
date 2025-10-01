package com.example.ui.screens.posts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme
import com.example.ui.R
import com.example.viewmodel.posts.PostsScreenUiState

@Composable
fun PostCard(
    post: PostsScreenUiState.PostUiState,
    onPostClick: (postId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onPostClick(post.id) },
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = AppTheme.color.contentD,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 17.dp),
        ) {
            Text(
                text = post.title,
                style = AppTheme.textStyle.captionOne.bold,
                color = AppTheme.color.primaryA,
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = post.body,
                style = AppTheme.textStyle.body.medium,
                color = AppTheme.color.contentA,
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(
                text = stringResource(R.string.comments, post.commentsCount),
                style = AppTheme.textStyle.body.medium,
                color = AppTheme.color.primaryA,
                modifier = Modifier.align(alignment = Alignment.End),
            )
        }
    }
}

@Preview(name = "PostCard")
@Composable
private fun PreviewPostCard() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 100.dp)
                .padding(horizontal = 24.dp),
    ) {
        PostCard(
            post =
                PostsScreenUiState.PostUiState(
                    id = 1,
                    title = "Why We Love Music",
                    body = "Researchers are discovering how music affects the brain, helping us to make sense of its real emotional and social power...",
                    commentsCount = 10,
                ),
            onPostClick = {},
        )
    }
}
