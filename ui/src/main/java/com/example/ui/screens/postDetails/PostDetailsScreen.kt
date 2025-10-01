package com.example.ui.screens.postDetails

import android.system.Os.stat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme
import com.example.ui.R
import com.example.ui.screens.postDetails.components.CommentCard
import com.example.viewmodel.postDetails.PostDetailsUiState
import com.example.viewmodel.postDetails.PostDetailsViewModel
import com.example.viewmodel.posts.PostsScreenUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PostDetailsViewModel = koinViewModel(),
) {
    val uiState = viewModel.state.collectAsState()

    PostDetailsContent(
        uiState.value,
        viewModel::toggleFavorite,
        {},
        modifier,
    )
}

@Composable
private fun PostDetailsContent(
    state: PostDetailsUiState,
    onFavoriteClick: (Int) -> Unit,
    onNavigateBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(AppTheme.color.contentC),
    ) {
        TopBar(state, onNavigateBackClick, onFavoriteClick, Modifier.padding(top = 48.dp))
        PostContent(state.postUiState, Modifier.padding(top = 24.dp))
        CommentSection(
            state.comments,
            Modifier
                .padding(top = 14.dp)
                .weight(1f),
        )
    }
}

@Composable
fun CommentSection(
    comments: List<PostDetailsUiState.PostDetailsCommentUiState>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 24.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp,
                ),
            ).background(AppTheme.color.primaryB)
            .padding(horizontal = 24.dp),
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_comment),
                    contentDescription = null,
                    tint = AppTheme.color.primaryA,
                )

                Text(
                    text = stringResource(R.string.comment, comments.size),
                    style = AppTheme.textStyle.captionTwo.bold,
                    color = AppTheme.color.primaryA,
                )
            }
        }

        items(comments) { comment ->
            CommentCard(
                comment,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun PostContent(
    post: PostsScreenUiState.PostUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 24.dp),
    ) {
        Text(
            text = post.title,
            style = AppTheme.textStyle.title.bold,
            color = AppTheme.color.primaryA,
        )

        Text(
            text = post.body,
            style = AppTheme.textStyle.captionOne.medium,
            color = AppTheme.color.contentA,
        )
    }
}

@Composable
private fun TopBar(
    state: PostDetailsUiState,
    onNavigateBackClick: () -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
    ) {
        IconButton(onClick = onNavigateBackClick) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_left_filled),
                contentDescription = stringResource(R.string.navigate_back_button),
                tint = AppTheme.color.primaryA,
            )
        }

        IconToggleButton(
            checked = state.postUiState.isFavorite,
            onCheckedChange = { onFavoriteClick(state.postUiState.id) },
        ) {
            Icon(
                imageVector =
                    ImageVector.vectorResource(
                        if (state.postUiState.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_filled,
                    ),
                contentDescription = stringResource(R.string.navigate_back_button),
                tint = AppTheme.color.primaryA,
            )
        }
    }
}

@Preview(name = "PostDetailsScreen")
@Composable
private fun PreviewPostDetailsScreen() {
    PostDetailsContent(
        state =
            PostDetailsUiState(
                postUiState =
                    PostsScreenUiState.PostUiState(
                        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                        body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
                        isFavorite = true,
                    ),
                comments =
                    listOf(
                        PostDetailsUiState.PostDetailsCommentUiState(
                            name = "id labore ex et quam laborum",
                            email = "Eliseo@gardner.biz",
                            body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium",
                        ),
                        PostDetailsUiState.PostDetailsCommentUiState(
                            name = "id labore ex et quam laborum",
                            email = "Eliseo@gardner.biz",
                            body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium",
                        ),
                        PostDetailsUiState.PostDetailsCommentUiState(
                            name = "id labore ex et quam laborum",
                            email = "Eliseo@gardner.biz",
                            body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium",
                        ),
                        PostDetailsUiState.PostDetailsCommentUiState(
                            name = "id labore ex et quam laborum",
                            email = "Eliseo@gardner.biz",
                            body = "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium",
                        ),
                    ),
            ),
        {},
        {},
    )
}
