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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.theme.AppTheme
import com.example.ui.screens.posts.components.PostCard
import com.example.ui.screens.posts.components.TabRow
import com.example.ui.screens.posts.components.TabTitle
import com.example.viewmodel.posts.PostsInteractionListener
import com.example.viewmodel.posts.PostsScreenUiState
import com.example.viewmodel.posts.PostsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = koinViewModel(),
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    PostsScreenContent(
        state = uiState.value,
        listener = viewModel,
        modifier = modifier,
    )
}

@Composable
private fun PostsScreenContent(
    state: PostsScreenUiState,
    listener: PostsInteractionListener,
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
                listener::onTabSelected,
                Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
            )
        }

        item {
            Spacer(modifier = Modifier.padding(32.dp))
        }

        Posts(posts = state.posts, onPostClick = listener::onPostClick)
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
            "All",
            "Favorite",
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
            "Hi, what do \n" +
                "you want to read",
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
        listener =
            object : PostsInteractionListener {
                override fun onPostClick(postId: Int) {}

                override fun onTabSelected(tabIndex: Int) {}
            },
        modifier = Modifier.fillMaxSize(),
    )
}
