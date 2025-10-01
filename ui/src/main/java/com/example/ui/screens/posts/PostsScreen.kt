package com.example.ui.screens.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.theme.AppTheme
import com.example.ui.R
import com.example.ui.components.LoadingPlaceholder
import com.example.ui.components.NoNetworkPlaceholder
import com.example.ui.navigation.Route
import com.example.ui.screens.posts.components.NoDataFoundPlaceholder
import com.example.ui.screens.posts.components.PostCard
import com.example.ui.screens.posts.components.TabRow
import com.example.ui.screens.posts.components.TabTitle
import com.example.viewmodel.posts.PostsScreenUiState
import com.example.viewmodel.posts.PostsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = koinViewModel(),
    navController: NavController = rememberNavController(),
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    PostsScreenContent(
        state = uiState.value,
        onPostClick = {
            val post = uiState.value.posts[it.dec()]
            navController.navigate(
                Route.PostDetails(
                    postId = post.id,
                    postTitle = post.title,
                    postBody = post.body,
                ),
            )
        },
        onTabSelected = viewModel::onTabSelected,
        onRetryClick = viewModel::retryFetchingPosts,
        modifier = modifier,
    )
}

@Composable
private fun PostsScreenContent(
    state: PostsScreenUiState,
    onPostClick: (Int) -> Unit,
    onTabSelected: (Int) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = AppTheme.color.primaryB),
    ) {
        item {
            WelcomeMessage(
                Modifier
                    .padding(top = 88.dp)
                    .padding(horizontal = 24.dp),
            )
        }
        stickyHeader {
            TapLayout(
                state.currentSelectedTabIndex,
                onTabSelected,
                Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
            )
        }

        item {
            Spacer(modifier = Modifier.padding(32.dp))
        }

        if (state.isLoading) {
            item { LoadingPlaceholder(modifier = Modifier.padding(top = 220.dp)) }
        } else if (state.isNoInternetConnection) {
            item {
                NoNetworkPlaceholder(onRetryClick, modifier = Modifier.padding(top = 120.dp))
            }
        } else if (state.posts.isEmpty()) {
            item { NoDataFoundPlaceholder(modifier = Modifier.padding(top = 220.dp)) }
        } else {
            Posts(posts = state.posts, onPostClick = onPostClick)
        }
    }
}

private fun LazyListScope.Posts(
    posts: List<PostsScreenUiState.PostUiState>,
    onPostClick: (postId: Int) -> Unit,
) {
    items(posts) { post ->
        PostCard(post, onPostClick)
    }
}

@Composable
private fun TapLayout(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs =
        listOf(
            stringResource(R.string.post),
            stringResource(R.string.favorite),
        )
    TabRow(
        selectedTabPosition = selectedTabIndex,
        modifier = modifier.fillMaxWidth(),
    ) {
        tabs.forEachIndexed { index, title ->
            TabTitle(title, index, selectedTabIndex == index, onTabSelected)
        }
    }
}

@Composable
private fun WelcomeMessage(modifier: Modifier = Modifier) {
    Text(
        text =
            stringResource(R.string.hi_what_do_you_want_to_read),
        style = AppTheme.textStyle.title.bold,
        color = AppTheme.color.primaryA,
        modifier = modifier,
    )
}

@Preview(name = "PostsScreen")
@Composable
private fun PreviewPostsScreen() {
    PostsScreenContent(
        state =
            PostsScreenUiState(
                posts =
                    List(10) {
                        PostsScreenUiState.PostUiState(
                            id = it,
                            title = "Post Title $it",
                            body = "Post Body $it",
                            commentsCount = it * 3,
                        )
                    },
            ),
        {},
        {},
        {},
        modifier = Modifier.fillMaxSize(),
    )
}
